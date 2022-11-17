package hello.springtx.propagation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final LogRepository logRepository;

    public void joinV1(String username) {
        Member member = new Member(username);
        Log logMessage = new Log(username);

        log.info("memberRepository begin");
        memberRepository.save(member);
        log.info("memberRepository end");

        log.info("logRepository begin");
        logRepository.save(logMessage);
        log.info("logRepository end");
    }

    public void joinV2(String username) {
        Member member = new Member(username);
        Log logMessage = new Log(username);

        log.info("memberRepository begin");
        memberRepository.save(member);
        log.info("memberRepository end");

        log.info("logRepository begin");
        try {
            logRepository.save(logMessage);
        } catch (RuntimeException e) {
            log.info("log save fail. logMessage={}", logMessage.getMessage());
            log.info("back to normal case");
        }
        log.info("logRepository end");
    }
}
