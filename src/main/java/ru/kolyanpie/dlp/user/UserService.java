package ru.kolyanpie.dlp.user;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserService {
    private static final String GUEST_PREFIX = "GUEST-";

    private LoadingCache<String, String> users;

    @Autowired
    public void setUsers(@Value("${user.expire.duration}") int expireDur) {
        CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();
        if (expireDur >= 0) {
            cacheBuilder.expireAfterAccess(expireDur, TimeUnit.MINUTES);
        }
        users = cacheBuilder.build(
                new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) {
                        return null;
                    }
                });
    }

    public String loginGuest(String name) {
        validateName(name);
        String id = GUEST_PREFIX + UUID.randomUUID();
        while (users.asMap().containsKey(id)) {
            id = GUEST_PREFIX + UUID.randomUUID();
        }
        users.asMap().put(id, name);
        return id;
    }

    public String loginUser(String id, String name) {
        validateName(name);
        if (users.asMap().containsKey(id)) {
            throw new IllegalArgumentException();
        }
        users.asMap().put(id, name);
        return id;
    }

    public void logoutUser(String id, String name) {
        users.asMap().remove(id, name);
    }

    private void validateName(String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("Name must contain text");
        }
        if (name.contains(" ")) {
            throw new IllegalArgumentException("Name must not contain spaces");
        }
        if (name.length() < 4 || name.length() > 32) {
            throw new IllegalArgumentException("Name must contain 4-32 symbols");
        }
    }

    public Map<String, String> list() {
        return users.asMap();
    }
}
