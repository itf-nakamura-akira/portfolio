package dev.yukikaze.portfolio.services;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.yukikaze.portfolio.entities.UsersEntity;
import dev.yukikaze.portfolio.mappers.UsersMapper;

/**
 * 認証 Service
 */
@Service
@Transactional
public class AuthenticationService {
    /**
     * パスワードエンコーダー(BCrypt)
     */
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * UsersMapper
     */
    private final UsersMapper usersMapper;

    /**
     * コンストラクター
     */
    public AuthenticationService(UsersMapper usersMapper) {
        this.usersMapper = usersMapper;
    }

    /**
     * アカウント・パスワードの検証処理
     *
     * @param account  アカウント
     * @param password パスワード
     *
     * @return 検証結果
     */
    public Optional<UsersEntity> verify(String account, String password) {
        // アカウントの取得
        Optional<UsersEntity> user = this.usersMapper.selectByAccount(account);

        // 有効なアカウントが見つからない
        if (user.isEmpty() || !user.get().getIsEnabled()) {
            return Optional.empty();
        }

        // パスワードの検証
        if (!this.verifyHashedPassword(password, user.get().getPasswordHash())) {
            return Optional.empty();
        }

        return user;
    }

    /**
     * 入力されたパスワードとハッシュ化されたパスワードを検証する
     *
     * @param password       入力されたパスワード
     * @param hashedPassword ハッシュ化されたパスワード
     *
     * @return 検証結果
     */
    private boolean verifyHashedPassword(String password, String hashedPassword) {
        return passwordEncoder.matches(password, hashedPassword);
    }
}
