package hr.pishe.web.rest.component.impl;

import hr.pishe.service.EventService;
import hr.pishe.service.UserService;
import hr.pishe.service.dto.EventDTO;
import hr.pishe.service.dto.UserDTO;
import hr.pishe.service.mapper.UserMapper;
import hr.pishe.web.rest.component.EventComponent;
import hr.pishe.web.rest.vm.EventFilterVM;
import hr.pishe.web.rest.vm.response.ResponseVM;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.*;

@Component
public class EventComponentImpl implements EventComponent {

    private final Logger log = LoggerFactory.getLogger(EventComponentImpl.class);
    private final EventService eventService;
    private final UserService userService;
    private final UserMapper userMapper;

    public EventComponentImpl(EventService eventService,
                              UserService userService,
                              UserMapper userMapper) {
        this.eventService = eventService;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Override
    public ResponseVM<EventDTO> findById(Long id) throws NotFoundException {
        log.debug("Request to find event via component by id : {}", id);
        EventDTO eventDTO = eventService.findById(id);
        ResponseVM<EventDTO> responseVM = new ResponseVM<>();
        responseVM.setData(eventDTO);
        responseVM.setInclude(this.createIncluded(eventDTO));
        return responseVM;
    }

    @Override
    public List<ResponseVM<EventDTO>> findByOwnerId(EventFilterVM requestParam) throws NotFoundException {
        log.debug("Request to find event via component by ownerId");
        List<EventDTO> eventDTOS = eventService.findByOwnerId(requestParam);
        List<ResponseVM<EventDTO>> responseVMS = new ArrayList<>();
        for (EventDTO eventDTO : eventDTOS) {
            ResponseVM<EventDTO> responseVM = new ResponseVM<>();
            responseVM.setData(eventDTO);
            responseVM.setInclude(this.createIncluded(eventDTO));
            responseVMS.add(responseVM);
        }
        return responseVMS;
    }

    @Override
    public List<ResponseVM<EventDTO>> findByOwnerIdAndDate(ZonedDateTime start, ZonedDateTime end) throws NotFoundException {
        log.debug("Request to find event via component by ownerId and date : {}, {}", start, end);
        List<EventDTO> eventDTOS = eventService.findByOwnerIdAndDate(start, end);
        List<ResponseVM<EventDTO>> responseVMS = new ArrayList<>();
        for (EventDTO eventDTO : eventDTOS) {
            ResponseVM<EventDTO> responseVM = new ResponseVM<>();
            responseVM.setData(eventDTO);
            responseVM.setInclude(this.createIncluded(eventDTO));
            responseVMS.add(responseVM);
        }
        return responseVMS;
    }

    private Map<String, Object> createIncluded(EventDTO eventDTO) {
        Map<String, Object> included = new HashMap<>();

        Optional<UserDTO> userDTOOptional = userService.findById(eventDTO.getOwnerId());
        userDTOOptional.ifPresent(userDTO -> included.put("user", userMapper.dtoToVm(userDTO)));

        return included;
    }
}
