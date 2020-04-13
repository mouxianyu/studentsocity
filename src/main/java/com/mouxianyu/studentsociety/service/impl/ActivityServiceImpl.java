package com.mouxianyu.studentsociety.service.impl;

import com.mouxianyu.studentsociety.common.config.ImgConfig;
import com.mouxianyu.studentsociety.common.enums.ActivityScaleEnum;
import com.mouxianyu.studentsociety.common.enums.ObjTypeEnum;
import com.mouxianyu.studentsociety.common.enums.ScaleEnum;
import com.mouxianyu.studentsociety.common.enums.StatusEnum;
import com.mouxianyu.studentsociety.mapper.ActivityMapper;
import com.mouxianyu.studentsociety.pojo.dto.ActivityDTO;
import com.mouxianyu.studentsociety.pojo.entity.Activity;
import com.mouxianyu.studentsociety.pojo.entity.Img;
import com.mouxianyu.studentsociety.pojo.entity.Society;
import com.mouxianyu.studentsociety.pojo.entity.User;
import com.mouxianyu.studentsociety.pojo.vo.ActivityVO;
import com.mouxianyu.studentsociety.service.ActivityService;
import com.mouxianyu.studentsociety.service.ImgService;
import com.mouxianyu.studentsociety.service.SocietyService;
import com.mouxianyu.studentsociety.service.UserService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description: TODO
 * @author: kingsme@yeah.net
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private SocietyService societyService;

    @Autowired
    private ImgConfig imgConfig;

    @Autowired
    private ImgService imgService;


    @Override
    public List<ActivityVO> queryByPage(ActivityDTO activityDTO) {
        List<ActivityVO> activityVOS = new ArrayList<>();
        Example example = condition(activityDTO);
        RowBounds rowBounds = new RowBounds(activityDTO.getStart(), activityDTO.getRow());
        List<Activity> activities = activityMapper.selectByExampleAndRowBounds(example, rowBounds);
        for (Activity activity : activities) {
            ActivityVO activityVO = toVOCondition(activity);
            activityVOS.add(activityVO);
        }
        return activityVOS;
    }

    @Override
    public List<ActivityVO> queryByPageWithImg(ActivityDTO activityDTO) {
        List<ActivityVO> activityVOS = new ArrayList<>();
        Example example = condition(activityDTO);
        RowBounds rowBounds = new RowBounds(activityDTO.getStart(), activityDTO.getRow());
        List<Activity> activities = activityMapper.selectByExampleAndRowBounds(example, rowBounds);
        for (Activity activity : activities) {
            ActivityVO activityVO = toVOConditionWithImg(activity);
            activityVOS.add(activityVO);
        }
        return activityVOS;
    }

    @Override
    public int getCountByCondition(ActivityDTO activityDTO) {
        Example example = condition(activityDTO);
        return activityMapper.selectCountByExample(example);
    }

    @Override
    public Activity getById(Long id) {
        return activityMapper.selectByPrimaryKey(id);
    }

    @Override
    public ActivityVO getByIdMore(Long id) {
        Activity activity = getById(id);
        return toVOConditionWithImg(activity);
    }

    @Override
    public void deleteById(Long id) {
        Activity activity = new Activity();
        activity.setId(id);
        activity.setStatus(StatusEnum.DELETED.getCode());
        activityMapper.updateByPrimaryKeySelective(activity);
        imgService.deleteByTypeAndObjId(id, ObjTypeEnum.ACTIVITY.getCode());
    }

    @Override
    public void deleteByIds(Long[] ids) {
        for (Long id : ids) {
            deleteById(id);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateById(Activity activity, MultipartFile[] multipartFiles) throws IOException {
        activity.setModifyTime(new Date());
        activityMapper.updateByPrimaryKeySelective(activity);
        if (multipartFiles != null) {
            if (multipartFiles.length > 0) {
                for (MultipartFile multipartFile : multipartFiles) {
                    imgService.add(multipartFile, imgConfig.getActivity(), ObjTypeEnum.ACTIVITY.getCode(), activity.getId());
                }
            }
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long add(Activity activity, MultipartFile[] multipartFiles) throws IOException {
        activity.setCreateTime(new Date());
        if (activity.getStatus() == null) {
            activity.setStatus(StatusEnum.AUDITING.getCode());
        }
        if (activity.getSocietyId() != null) {
            activity.setScale(ActivityScaleEnum.SOCIETY.getCode());
        }
        activityMapper.insertSelective(activity);
        if (multipartFiles != null && multipartFiles.length > 0) {
            for (MultipartFile multipartFile : multipartFiles) {
                imgService.add(multipartFile, imgConfig.getActivity(), ObjTypeEnum.ACTIVITY.getCode(), activity.getId());
            }
        }
        return activity.getId();
    }

    private Example condition(ActivityDTO activityDTO) {
        Example example = new Example(Activity.class);
        example.orderBy("id").desc();
        Example.Criteria criteria = example.createCriteria();
        if (activityDTO.getStatus() == null) {
            criteria.andNotEqualTo("status", StatusEnum.DELETED.getCode());
        } else {
            criteria.andEqualTo("status", activityDTO.getStatus());
        }
        if (activityDTO.getScale() != null) {
            criteria.andEqualTo("scale", activityDTO.getScale());
        }
        if (!StringUtils.isEmpty(activityDTO.getTitle())) {
            criteria.andLike("title", "%" + activityDTO.getTitle() + "%");
        }
        if (activityDTO.getSocietyId() != null) {
            criteria.andEqualTo("societyId", activityDTO.getSocietyId());
        }
        return example;
    }

    private ActivityVO toVOCondition(Activity activity) {
        ActivityVO activityVO = new ActivityVO();
        BeanUtils.copyProperties(activity, activityVO);
        if (activity.getCreateId() != null) {
            User user = userService.getById(activity.getCreateId());
            if (user != null) {
                activityVO.setCreateName(user.getName());
            }
        }
        if (activity.getSocietyId() != null) {
            Society society = societyService.getById(activity.getSocietyId());
            if (society != null) {
                activityVO.setSocietyName(society.getName());
            }
        }
        return activityVO;
    }

    private ActivityVO toVOConditionWithImg(Activity activity) {
        ActivityVO activityVO = toVOCondition(activity);
        List<Img> imgs = imgService.queryByTypeObjId(activity.getId(), ObjTypeEnum.ACTIVITY.getCode());
        if (imgs != null && imgs.size() > 0) {
            activityVO.setImgs(imgs);
        }
        List<Img> avatar = imgService.queryByTypeObjId(activity.getCreateId(), ObjTypeEnum.AVATAR.getCode());
        if (avatar != null && avatar.size() > 0) {
            activityVO.setCreateAvatar(avatar.get(0).getRelName());
        }
        return activityVO;
    }
}
