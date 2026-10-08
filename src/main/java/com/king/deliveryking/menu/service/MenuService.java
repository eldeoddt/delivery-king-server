package com.king.deliveryking.menu.service;

import com.king.deliveryking.global.exception.BusinessException;
import com.king.deliveryking.global.exception.ErrorCode;
import com.king.deliveryking.menu.MenuStatus;
import com.king.deliveryking.menu.dto.response.MenuResponseDTO;
import com.king.deliveryking.menu.dto.request.MenuRequestDTO;
import com.king.deliveryking.menu.entity.Menu;
import com.king.deliveryking.menu.repository.MenuRepository;
import com.king.deliveryking.user.entity.User;
import com.king.deliveryking.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuService {

    private final MenuRepository menuRepository;
    private final UserRepository userRepository;

    public MenuResponseDTO createMenu(Long userId, @Valid MenuRequestDTO request) {

        User user = userRepository.findById(userId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Menu menu = Menu.builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .user(user)
                .build();

        return MenuResponseDTO.from(menu);
    }

    // list 조회
    @Transactional(readOnly = true)
    public List<MenuResponseDTO> getMenuList() {
        return menuRepository.findAllByStatus(MenuStatus.ACTIVE).stream()
                .map(MenuResponseDTO::from)
                .toList();
    }

    // 단건 조회
    @Transactional(readOnly = true)
    public MenuResponseDTO getMenu(Long menuId) {
        Menu menu = menuRepository.findByIdAndStatus(menuId, MenuStatus.ACTIVE)
                .orElseThrow(() -> new BusinessException(ErrorCode.MENU_NOT_FOUND));

        return MenuResponseDTO.from(menu);
    }

    public MenuResponseDTO updateMenu(Long userId, Long menuId, @Valid MenuRequestDTO request) {
        Menu menu = menuRepository.findByIdAndStatus(menuId, MenuStatus.ACTIVE)
                .orElseThrow(() -> new BusinessException(ErrorCode.MENU_NOT_FOUND)); // 404

        // 다른 사장 에뉴 -> 403
        if (!menu.getUser().getId().equals(userId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }

        menu.update(request.name(), request.price(), request.description());

        return MenuResponseDTO.from(menu);
    }


    public void deleteMenu(Long userId, Long menuId) {
        Menu menu = menuRepository.findByIdAndStatus(menuId, MenuStatus.ACTIVE)
                .orElseThrow(() -> new BusinessException(ErrorCode.MENU_NOT_FOUND)); // 404

        // 다른 사장 에뉴 -> 403
        if (!menu.getUser().getId().equals(userId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }

        menu.setStatus(MenuStatus.DELETED);
    }
}
