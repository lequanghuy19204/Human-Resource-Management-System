package com.example.ejb;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import com.example.dao.RewardDao;
import com.example.entity.Reward;
import java.util.List;

@Stateless
public class RewardServiceBean {

    @Inject
    private RewardDao rewardDao;

    public void addReward(Reward reward) {
        rewardDao.add(reward);
    }

    public void updateReward(Reward reward) {
        rewardDao.update(reward);
    }

    public void removeReward(String id) {
        rewardDao.removeById(id);
    }

    public boolean hasReward(String id) {
        return rewardDao.has(id);
    }

    public List<Reward> getAllRewards() {
        return rewardDao.getAll();
    }

    public List<Reward> getRewardsByEmployee(String employeeId) {
        return rewardDao.getWhere("employeeId", employeeId);
    }
}
