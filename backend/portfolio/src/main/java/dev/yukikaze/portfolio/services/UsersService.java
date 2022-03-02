package dev.yukikaze.portfolio.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.yukikaze.portfolio.entities.UsersEntity;
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
    private UsersMapper usersMapper;

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
}
