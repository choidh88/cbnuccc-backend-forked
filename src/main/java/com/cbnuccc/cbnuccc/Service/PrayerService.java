package com.cbnuccc.cbnuccc.Service;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cbnuccc.cbnuccc.Dto.PrayerDto;
import com.cbnuccc.cbnuccc.Model.MyUser;
import com.cbnuccc.cbnuccc.Model.Prayer;
import com.cbnuccc.cbnuccc.Repository.PrayerJpaRepository;
import com.cbnuccc.cbnuccc.Repository.UserJpaRepository;
import com.cbnuccc.cbnuccc.Util.StatusCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrayerService {
    private final UserJpaRepository userJpaRepository;
    private final PrayerJpaRepository prayerJpaRepository;

    // get all prayers.
    // public List<PrayerDto> getAllPrayers() {
    // List<Prayer> prayers = prayerJpaRepository.findAll();
    // for (Prayer prayer : prayers) {

    // }
    // }

    // create a prayer.
    public StatusCode createPrayer(PrayerDto prayerDto, UUID uuid) {
        Optional<MyUser> _author = userJpaRepository.findByUuid(uuid);
        if (_author.isEmpty())
            return StatusCode.NO_USER_FOUND;
        MyUser author = _author.get();

        Prayer prayer = new Prayer();
        prayer.setCreatedAt(OffsetDateTime.now(ZoneId.of("Asia/Seoul")));
        prayer.setRequest(prayerDto.getRequest());
        prayer.setAnonymous(prayerDto.getAnonymous());
        prayer.setAuthor(author);

        try {
            prayerJpaRepository.save(prayer);
            return StatusCode.NO_ERROR;
        } catch (Exception e) {
            return StatusCode.SOMETHING_WENT_WRONG;
        }
    }
}
