package com.devsplan.ketchup.reserve.controller;

import com.devsplan.ketchup.reserve.dto.ReserveDTO;
import com.devsplan.ketchup.reserve.service.ReserveService;
import com.devsplan.ketchup.rsc.dto.ResourceDTO;
import com.devsplan.ketchup.rsc.entity.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.devsplan.ketchup.util.TokenUtils.decryptToken;

@RestController
@RequestMapping("/reserves")
@Slf4j
public class ReserveController {

    private final ReserveService reserveService;
    List<ReserveDTO> reserve;

    public ReserveController(ReserveService reserveService) {
        this.reserveService = reserveService;
        reserve = new ArrayList<>();

        // LocalDateTime 파싱을 위한 DateTimeFormatter 생성
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd a h시 m분", Locale.KOREAN);

        // 날짜 및 시간 정보를 문자열에서 LocalDateTime으로 변환하여 ScheduleDTO 객체를 생성
        LocalDateTime rsvStartDttm = LocalDateTime.parse("2024-05-20 오후 4시 30분", formatter);
        LocalDateTime rsvEndDttm = LocalDateTime.parse("2024-05-20 오후 7시 30분", formatter);

        reserve.add(new ReserveDTO(6, "ReserveController에서 생성하는 사용 목적", rsvStartDttm, rsvEndDttm, "3", new ResourceDTO(1, "회의실", "회의실 B", "본관 4층 401호", 20, true, "Smart TV, 빔프로젝터, 책상, 의자, 단상 비치, 경복궁 뷰")));
    }

    @GetMapping
    public ResponseEntity<List<ReserveDTO>> getReserves(@RequestParam("category") String rscCategory,
                                                        @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate rsvDate) {
        LocalDateTime startOfDay = rsvDate.atStartOfDay();
        LocalDateTime endOfDay = rsvDate.atTime(LocalTime.MAX);

        List<ReserveDTO> reserves = reserveService.getReserveWithResources(rscCategory, startOfDay, endOfDay);
        return ResponseEntity.ok(reserves);
    }


//    // 자원 예약 상세 조회
//    @GetMapping("/{rsvNo}")
//    public ResponseEntity<ResponseMessage> selectReserveDetail(@PathVariable int rsvNo) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//
//        ReserveDTO selectedReserve = reserveService.selectReserveDetail(rsvNo);
//        for (ReserveDTO reserveDTO : reserve) {
//            if (reserveDTO.getRsvNo() == rsvNo) {
//                selectedReserve = reserveDTO;
//                break;
//            }
//        }
//
//        if (selectedReserve != null) {
//            // ReserveDTO를 Map으로 변환
//            Map<String, Object> responseData = new HashMap<>();
//            responseData.put("rsvStartDttm", selectedReserve.getRsvStartDttm());
//            responseData.put("rsvEndDttm", selectedReserve.getRsvEndDttm());
//            responseData.put("rsvDescr", selectedReserve.getRsvDescr());
//
//            // ResponseEntity에 Map을 설정하여 반환
//            return ResponseEntity.ok().headers(headers).body(new ResponseMessage(200, "조회 성공", responseData));
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(404, "해당 자원 예약건을 찾을 수 없습니다.", null));
//        }
//    }

    // 자원 예약 등록
    @PostMapping()
    public ResponseEntity<?> insertReserve(@RequestBody ReserveDTO newReserve) {
        log.debug("newReserve: {}", newReserve);

        if (newReserve.getResources() == null) {
            return ResponseEntity.badRequest().body("자원 정보가 비어있습니다.");
        }

        Resource resource = reserveService.findResourceById(newReserve.getResources().getRscNo());
        if (resource == null) {
            return ResponseEntity.badRequest().body("해당 자원을 찾을 수 없습니다.");
        }

        reserveService.insertReserve(newReserve, resource);

        return ResponseEntity.created(URI.create("/reserves/" + newReserve.getRsvNo())).build();
    }

    // 자원 예약 수정
    @PutMapping("/{rsvNo}")
    public ResponseEntity<?> updateSchedule(@PathVariable int rsvNo,
                                            @RequestBody ReserveDTO updateReserve,
                                            @RequestHeader("Authorization") String token) {

        String memberNo = decryptToken(token).get("memberNo", String.class);

        try {
            reserveService.updateReserve(rsvNo, memberNo, updateReserve);
            String uri = "/reserves/" + rsvNo;

            return ResponseEntity.ok().header(HttpHeaders.LOCATION, uri).body("예약이 성공적으로 수정되었습니다. URI: " + uri);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    // 자원 예약 삭제
    @DeleteMapping("/{rsvNo}")
    public ResponseEntity<?> deleteReserve(@PathVariable int rsvNo,
                                           @RequestHeader("Authorization") String token) {

        String memberNo = decryptToken(token).get("memberNo", String.class);

        try {
            reserveService.deleteById(rsvNo, memberNo);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}