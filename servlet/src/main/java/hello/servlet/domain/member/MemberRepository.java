package hello.servlet.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// There is no concurrency problem in this project. Therefore, ConcurrentHashMap and AtomicLong is not considered.
public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long seq = 0L;

    private static final MemberRepository instance = new MemberRepository();

    public static MemberRepository getInstance() {
        return instance;
    }

    private MemberRepository() {}

    public Member save(Member member) {
        member.setId(++seq);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id) {
        return store.get(id);
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
