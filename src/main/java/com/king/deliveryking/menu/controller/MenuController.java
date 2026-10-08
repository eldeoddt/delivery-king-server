package com.king.deliveryking.menu.controller;

import com.king.deliveryking.global.common.UserRole;
import com.king.deliveryking.global.security.LoginUserId;
import com.king.deliveryking.menu.dto.response.MenuResponseDTO;
import com.king.deliveryking.menu.dto.request.MenuRequestDTO;
import com.king.deliveryking.menu.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menus")
public class MenuController {

    private final MenuService menuService;

    // 사장만
    @Secured(UserRole.Authority.OWNER)
    @PostMapping
    public ResponseEntity<MenuResponseDTO> createMenu(
            @LoginUserId Long userId,
            @RequestBody @Valid MenuRequestDTO request) {
        return ResponseEntity.ok(menuService.createMenu(userId, request));
    }

    // 누구나 조회 가능
    @GetMapping("/list")
    public ResponseEntity<List<MenuResponseDTO>> getMenuList() {
        return ResponseEntity.ok(menuService.getMenuList());
    }

    @GetMapping("/{menuId}")
    public ResponseEntity<MenuResponseDTO> getMenu(
            @PathVariable Long menuId
    ) {
        return ResponseEntity.ok(menuService.getMenu(menuId));
    }

    // 본인만 수정 가능 이름 가격 설명
    // 다른 사장 메뉴: 403
    // 없는,삭제된 메뉴: 404
    @Secured(UserRole.Authority.OWNER)
    @PatchMapping("/{menuId}")
    public ResponseEntity<MenuResponseDTO> updateMenu(
            @LoginUserId Long userId,
            @PathVariable Long menuId,
            @RequestBody @Valid MenuRequestDTO request) {
        return ResponseEntity.ok(menuService.updateMenu(userId, menuId, request));
    }

    // soft delete, 본인만 삭제 가능.
    @Secured(UserRole.Authority.OWNER)
    @DeleteMapping("/{menuId}")
    public ResponseEntity<Void> deleteMenu(
            @LoginUserId Long userId,
            @PathVariable Long menuId) {

        menuService.deleteMenu(userId, menuId);

        return ResponseEntity.ok().build();
    }
}