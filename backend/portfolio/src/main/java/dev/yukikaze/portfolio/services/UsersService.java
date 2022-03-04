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
    public List<UsersEntity> findAll() {
        List<UsersEntity> usersList = this.usersMapper.findAll();

        return usersList;
    }

    /**
     * ユーザーIDを指定してユーザーデータを取得する
     * 
     * @param userId ユーザーID
     * 
     * @return ユーザーデータ
     */
    public UsersEntity findById(Long userId) {
        UsersEntity user = this.usersMapper.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "存在しないユーザーIDが指定されました。"));

        return user;
    }

    /**
     * ユーザーデータを追加する
     * 
     * @param account アカウント
     * @param password パスワード
     * @param name 表示名
     * @param permission ユーザー権限
     * @param isEnabled 有効フラグ
     * 
     * @return 追加したユーザーのデータ
     */
    public UsersEntity insertUser(String account, String password, String name,
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
