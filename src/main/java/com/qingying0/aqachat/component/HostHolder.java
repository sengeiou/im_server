
package com.qingying0.aqachat.component;

import com.qingying0.aqachat.entity.User;
import org.springframework.stereotype.Component;

@Component
public class HostHolder {
    private ThreadLocal<Long> localUserId = new ThreadLocal<>();

    public void setUserId(Long userId) {
        localUserId.set(userId);
    }

    public Long getUserId() {
        return localUserId.get();
    }

    public void delete() {
        localUserId.remove();
    }
}
