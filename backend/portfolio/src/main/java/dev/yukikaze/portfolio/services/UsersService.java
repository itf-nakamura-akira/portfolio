package dev.yukikaze.portfolio.services;

import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import dev.yukikaze.portfolio.entities.UsersEntity;
import dev.yukikaze.portfolio.enums.UsersPermission;
import dev.yukikaze.portfolio.mappers.UsersMapper;

/**
 * users テーブルに関するビジネスロジック
 */
@Service
@Transactional
public class UsersService {
    /**
     * UsersMapper
     */
    private final UsersMapper usersMapper;

    /**
     * コンストラクター
     *
     * @param usersMapper UsersMapper
     */
    public UsersService(UsersMapper usersMapper) {
        this.usersMapper = usersMapper;
    }

    /**
     * ユーザーデータを全件取得する
     *
     * @return ユーザーデータ全件
     */
    public List<UsersEntity> getUsersAll() {
        List<UsersEntity> usersList = this.usersMapper.selectAll();

        return usersList;
    }

    /**
     * ユーザーIDを指定してユーザーデータを取得する
     *
     * @param userId ユーザーID
     * 
     * @return ユーザーデータ
     */
    public UsersEntity getUserById(Long userId) {
        UsersEntity user = this.usersMapper.selectById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "ユーザー情報の取得に失敗しました。"));

        return user;
    }

    /**
     * ユーザーデータを追加する
     *
     * @param account    アカウント
     * @param password   パスワード
     * @param name       表示名
     * @param permission ユーザー権限
     * @param isEnabled  有効フラグ
     * 
     * @return 追加したユーザーのデータ
     */
    public UsersEntity registUser(String account, String password, String name,
            UsersPermission permission, Boolean isEnabled) {
        String hashedPassword = this.getHashedPassword(password);
        var addUser = new UsersEntity();
        addUser.setAccount(account);
        addUser.setPasswordHash(hashedPassword);
        addUser.setName(name);
        addUser.setPermission(permission);
        addUser.setIsEnabled(isEnabled);

        try {
            this.usersMapper.insertUser(addUser);
        } catch (DuplicateKeyException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "「" + account + "」というユーザーは既に存在しています。");
        }

        return addUser;
    }

    /**
     * ユーザーデータを更新する(パスワードは除く)
     *
     * @param id         ID
     * @param account    アカウント
     * @param name       表示名
     * @param permission ユーザー権限
     * @param isEnabled  有効フラグ
     * 
     * @return 更新したユーザーのデータ
     */
    public UsersEntity updateUser(Long id, String account, String name, UsersPermission permission,
            Boolean isEnabled) {
        try {
            if (!this.usersMapper.updateUser(id, account, name, permission, isEnabled)) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "更新対象のデータが見つかりませんでした。");
            }
        } catch (DuplicateKeyException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "「" + account + "」というユーザーは既に存在しています。");
        }

        return new UsersEntity(id, account, "", name, permission, isEnabled);
    }

    /**
     * ユーザーデータを削除する
     *
     * @param id ID
     */
    public void deleteUser(Long id) {
        if (!this.usersMapper.deleteUser(id)) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "対象のデータは既に削除されています。");
        }
    }

    /**
     * ハッシュ化したパスワード文字列を取得する
     *
     * @param password 元のパスワード文字列
     * 
     * @return ハッシュ化したパスワード文字列
     */
    private String getHashedPassword(String password) {
        var passwordEncoder = new BCryptPasswordEncoder();
        String hash = passwordEncoder.encode(password);

        return hash;
    }
}
