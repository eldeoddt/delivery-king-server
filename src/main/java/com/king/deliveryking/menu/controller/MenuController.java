package com.king.deliveryking.menu.controller;

import com.king.deliveryking.global.common.UserRole;
import com.king.deliveryking.global.security.LoginUserId;
import com.king.deliveryking.global.swagger.ApiErrorCodes;
import com.king.deliveryking.menu.dto.response.MenuResponseDTO;
import com.king.deliveryking.menu.dto.request.MenuRequestDTO;
import com.king.deliveryking.menu.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.king.deliveryking.global.exception.ErrorCode.*;

@Tag(name = "Menu", description = "메뉴 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menus")
public class MenuController {

    private final MenuService menuService;

    // 사장만
    @Operation(summary = "메뉴 등록", description = "OWNER만 가능. 메뉴 주인은 토큰의 사용자로 지정된다.")
    @ApiErrorCodes({INVALID_INPUT, UNAUTHORIZED, FORBIDDEN})
    @Secured(UserRole.Authority.OWNER)
    @PostMapping
    public ResponseEntity<MenuResponseDTO> createMenu(
            @Parameter(hidden = true) @LoginUserId Long userId,
            @RequestBody @Valid MenuRequestDTO request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(menuService.createMenu(userId, request));
    }

    // 누구나 조회 가능
    @Operation(summary = "메뉴 목록 조회", description = "로그인 없이 조회 가능. 삭제된 메뉴는 제외된다.")
    @SecurityRequirements
    @GetMapping
    public ResponseEntity<List<MenuResponseDTO>> getMenuList() {
        return ResponseEntity.ok(menuService.getMenuList());
    }

    @Operation(summary = "메뉴 단건 조회", description = "로그인 없이 조회 가능. 없거나 삭제된 메뉴면 404.")
    @ApiErrorCodes({INVALID_INPUT, MENU_NOT_FOUND})
    @SecurityRequirements
    @GetMapping("/{menuId}")
    public ResponseEntity<MenuResponseDTO> getMenu(
            @Parameter(description = "메뉴 id", example = "1") @PathVariable Long menuId
    ) {
        return ResponseEntity.ok(menuService.getMenu(menuId));
    }

    // 본인만 수정 가능 이름 가격 설명
    // 다른 사장 메뉴: 403
    // 없는,삭제된 메뉴: 404
    @Operation(summary = "메뉴 수정", description = "본인 메뉴만 수정 가능. 이름, 가격, 설명을 수정한다.")
    @ApiErrorCodes({INVALID_INPUT, UNAUTHORIZED, FORBIDDEN, MENU_NOT_FOUND})
    @Secured(UserRole.Authority.OWNER)
    @PatchMapping("/{menuId}")
    public ResponseEntity<MenuResponseDTO> updateMenu(
            @Parameter(hidden = true) @LoginUserId Long userId,
            @Parameter(description = "메뉴 id", example = "1") @PathVariable Long menuId,
            @RequestBody @Valid MenuRequestDTO request) {
        return ResponseEntity.ok(menuService.updateMenu(userId, menuId, request));
    }

    // soft delete, 본인만 삭제 가능.
    @Operation(summary = "메뉴 삭제", description = "본인 메뉴만 삭제 가능. 실제로 지우지 않고 삭제 상태로 표시한다.")
    @ApiErrorCodes({UNAUTHORIZED, FORBIDDEN, MENU_NOT_FOUND})
    @Secured(UserRole.Authority.OWNER)
    @DeleteMapping("/{menuId}")
    public ResponseEntity<Void> deleteMenu(
            @Parameter(hidden = true) @LoginUserId Long userId,
            @Parameter(description = "메뉴 id", example = "1") @PathVariable Long menuId) {

        menuService.deleteMenu(userId, menuId);

        return ResponseEntity.ok().build();
    }
}
