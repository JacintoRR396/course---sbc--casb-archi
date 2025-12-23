package com.ssdjr2.pi.admin_server.config;

import org.springframework.stereotype.Component;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.notify.AbstractEventNotifier;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class CustomStatusChangeNotifier extends AbstractEventNotifier {
	
	private static final String STATUS_OFFLINE = "OFFLINE";
	private static final String STATUS_UNKNOWN = "UNKNOWN";

    public CustomStatusChangeNotifier(InstanceRepository repository) {
        super(repository);
    }

    @Override
    protected Mono<Void> doNotify(final InstanceEvent event, final Instance instance) {
        return Mono.fromRunnable(() -> {
            if (event instanceof InstanceStatusChangedEvent statusEvent) {
                final var status = statusEvent.getStatusInfo().getStatus();
                final var serviceName = instance.getRegistration().getName();
                final var  instanceId = instance.getId().toString();

                if (STATUS_OFFLINE.equals(status) || STATUS_UNKNOWN.equals(status)) {
                    log.warn("[-] SERVICE INACTIVE: {} [{}]", serviceName, instanceId);
                } else if ("UP".equals(status)) {
                    log.info("[+] SERVICE ACTIVE: {} [{}]", serviceName, instanceId);
                } else {
                    log.info("[!] CHANGE OF STATUS: {} is now {}", serviceName, status);
                }
            }
        });
    }
}
