package dev.yukikaze.portfolio.controllers.masters;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import dev.yukikaze.portfolio.annotations.Permission;
import dev.yukikaze.portfolio.entities.UsersEntity;
import dev.yukikaze.portfolio.enums.UsersPermission;
import dev.yukikaze.portfolio.services.UsersService;

/**
 * ユーザー設定画面 Controller
 */
@RestController
@RequestMapping("/masters/usersMaster")
@Permission(UsersPermission.Admin)
public class UsersMasterController {
    /**
     * UsersService
     */
    private UsersService usersService;

    /**
     * コンストラクター
     *
     * @param usersService UsersService
     */
    public UsersMasterController(UsersService usersService) {
        this.usersService = usersService;
    }

    /**
     * ユーザーリスト一覧取得
     *
     * @return ユーザーリスト一覧
     */
    @GetMapping("list")
    public ListResponse list() {
        try {
            List<ListUsers> usersList = this.usersService.getUsersAll().stream()
                    .map(v -> new ListUsers(v.getId(), v.getAccount(), v.getName(),
                            v.getPermission(), v.getIsEnabled()))
                    .toList();

            return new ListResponse(usersList);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "ユーザー情報の取得に失敗しました。");
        }
    }

    /**
     * ユーザーの新規登録
     *
     * @param body ユーザー情報
     *
     * @throws Exception エラーレスポンス
     */
    @PostMapping
    public RegistUsersResponse regist(@RequestBody RegistRequestBody body) throws Exception {
        try {
            UsersEntity user = this.usersService.registUser(body.account(), body.name(),
                    body.password(), body.permission(), body.isEnabled());

            return new RegistUsersResponse(new ListUsers(user.getId(), user.getAccount(),
                    user.getName(), user.getPermission(), user.getIsEnabled()));
        } catch (Exception e) {
            if (e instanceof ResponseStatusException) {
                throw e;
            }

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "ユーザー情報の新規登録に失敗しました。");
        }
    }

    /**
     * ユーザーの更新
     *
     * @param body ユーザー情報
     *
     * @throws Exception エラーレスポンス
     */
    @PutMapping
    public void update(@RequestBody UpdateRequestBody body) throws Exception {
        try {
            this.usersService.updateUser(body.id(), body.account(), body.name(),
                    body.permission(), body.isEnabled());
        } catch (Exception e) {
            if (e instanceof ResponseStatusException) {
                throw e;
            }

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "ユーザー情報の更新に失敗しました。");
        }
    }

    /**
     * ユーザーの削除
     *
     * @param body 対象のユーザーID
     */
    @DeleteMapping
    public void delete(@RequestBody DeleteRequestBody body) {
        try {
            this.usersService.deleteUser(body.id());
        } catch (Exception e) {
            if (e instanceof ResponseStatusException) {
                throw e;
            }

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "ユーザー情報の削除に失敗しました。");
        }
    }

    /**
     * listメソッドのレスポンス
     */
    public record ListResponse(List<ListUsers> users) {
    }

    /**
     * listメソッドのレスポンス - users
     */
    public record ListUsers(long id, String account, String name, UsersPermission permission, boolean isEnabled) {
    }

    /**
     * registメソッドのリクエストボディー
     */
    public record RegistRequestBody(String account, String name, String password,
            UsersPermission permission, Boolean isEnabled) {
    }

    /**
     * registメソッドのレスポンスボディー
     */
    public record RegistUsersResponse(ListUsers user) {
    }

    /**
     * updateメソッドのリクエストボディー
     */
    public record UpdateRequestBody(Long id, String account, String name, UsersPermission permission,
            Boolean isEnabled) {
    }

    /**
     * deleteメソッドのリクエストボディー
     */
    public record DeleteRequestBody(Long id) {
    }
}
