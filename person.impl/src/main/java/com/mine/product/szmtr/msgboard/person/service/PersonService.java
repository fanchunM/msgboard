package com.mine.product.szmtr.msgboard.person.service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.mine.product.szmtr.msgboard.person.dao.ILoginHistoryDao;
import com.mine.product.szmtr.msgboard.person.dto.LoginHistoryDto;
import com.mine.product.szmtr.msgboard.person.model.LoginHistoryModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mine.platform.common.dto.PageDto;
import com.mine.platform.common.dto.PageableDto;
import com.mine.product.szmtr.msgboard.message.dto.MessageDto;
import com.mine.product.szmtr.msgboard.message.service.IMessageService;
import com.mine.product.szmtr.msgboard.person.dao.IPersonDao;
import com.mine.product.szmtr.msgboard.person.dto.PersonCursorDto;
import com.mine.product.szmtr.msgboard.person.dto.PersonDto;
import com.mine.product.szmtr.msgboard.person.model.Person;
import com.vgtech.platform.common.utility.VGUtility;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class PersonService implements IPersonService{
	private static final Logger logger = LoggerFactory.getLogger(PersonService.class);
	
	@Autowired
	private IPersonDao personDao;
	@Autowired
	private IMessageService messageService;
	@Autowired
	private ILoginHistoryDao loginHistoryDao;
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
    private IPersonCursorService psersonCursorService;
	
	@Override
	public void createPerson(String phone, String pwd, String userName,String registeredIP, String headPortraits) {
		if(!VGUtility.isEmpty(personDao.findByPhone(phone)))
			throw new RuntimeException("该号码已被注册,请重新选择!");
		if(!VGUtility.isEmpty(personDao.findByUserName(userName)))
			throw new RuntimeException("该用户名已被注册,请重新输入!");
		Person person = new Person();
		person.setPassword(pwd);
		person.setPhone(phone);
		person.setUserName(userName);
		person.setNickName(userName);
		person.setGender(IPersonService.GENDER.未知);
		person.setRegisteredIP(registeredIP);
		person.setHeadPortraits(headPortraits);
		personDao.saveAndFlush(person);
	}
	@Override
	public PersonDto loginPerson(String userName, String pwd) {
		if(VGUtility.isEmpty(personDao.findByUserName(userName)))
			throw new RuntimeException("该用户未被注册!");
		Person person = personDao.findByUserNameAndPassword(userName, pwd);
		if(VGUtility.isEmpty(person))
			throw new RuntimeException("密码错误!");
		else return convertToDto(person);
	}
	@Override
	public PersonDto getByUserName(String userName) {
		Person person = personDao.findByUserName(userName);
		return VGUtility.isEmpty(person) ? null : convertToDto(person);
	}
	/**
	 * convert
	 */
	private PersonDto convertToDto(Person model) {
		PersonDto personDto = new PersonDto();
		personDto.setId(model.getId());
		if(!VGUtility.isEmpty(model.getUserName()))
			personDto.setUserName(model.getUserName());
		if(!VGUtility.isEmpty(model.getPassword()))
			personDto.setPassword(model.getPassword());
		if(!VGUtility.isEmpty(model.getWeixinId()))
			personDto.setWeixinId(model.getWeixinId());
		if(!VGUtility.isEmpty(model.getPhone()))
			personDto.setPhone(model.getPhone());
		if(!VGUtility.isEmpty(model.getGender()))
			personDto.setGender(model.getGender().toString());
		if(!VGUtility.isEmpty(model.getNickName()))
			personDto.setNickName(model.getNickName());
		if(!VGUtility.isEmpty(model.getPersonStatus()))
			personDto.setPersonStatus(model.getPersonStatus().toString());
		if(!VGUtility.isEmpty(model.getValidateCode()))
			personDto.setValidateCode(model.getValidateCode());
		if(!VGUtility.isEmpty(model.getRegisteredIP()))
			personDto.setRegisteredIP(model.getRegisteredIP());
		if(!VGUtility.isEmpty(model.getRecentLoginIP()))
			personDto.setRecentLoginIP(model.getRecentLoginIP());
		if(!VGUtility.isEmpty(model.getHeadPortraits())) 
			personDto.setHeadPortraits(model.getHeadPortraits());
		if(!VGUtility.isEmpty(model.getCreateTimestamp()))
			personDto.setCreateTimestamp(VGUtility.toDateStr(model.getCreateTimestamp(), "yyyy-MM-dd HH:mm:ss"));
		if(!VGUtility.isEmpty(model.getLastUpdateTimestamp()))
			personDto.setLastUpdateTimestamp(VGUtility.toDateStr(model.getLastUpdateTimestamp(), "yyyy-MM-dd"));
//		if(!VGUtility.isEmpty(model.getUploadHeadImage()))
//            personDto.setUploadHeadImage(model.getUploadHeadImage());
		return personDto;
	}
	@Override
	public PersonDto getOneById(String id) {
		if(!VGUtility.isEmpty(id)) 
			return convertToDto(personDao.getById(id));
		else return null;
	}
	@Override
	public PersonDto forgetPwdLoginByValidate(String phone, String validate) {
		if(VGUtility.isEmpty(phone))
			throw new RuntimeException("请输入手机号!");
		if(VGUtility.isEmpty(validate))
			throw new RuntimeException("请输入验证码!");
		Person person = personDao.findByPhoneAndValidateCode(phone,validate);
		if(VGUtility.isEmpty(person))
			throw new RuntimeException("请核对手机号或者验证码是否有误!");
		else return convertToDto(person);
	}
	@Override
	public PersonDto changeNickName(String userId, String nickName) {
		if(!VGUtility.isEmpty(personDao.findByNickName(nickName))&&!personDao.getById(userId).getNickName().equals(nickName))
			throw new RuntimeException("该昵称已被使用,请重新输入!");
		Optional<Person> personOpt = personDao.findById(userId);
		if(personOpt.isPresent()) {
			Person person = personOpt.get();
			person.setNickName(nickName);
			return convertToDto(personDao.save(person));
		}
		return null;
	}
	@Override
	public PersonDto changePhone(String userId, String phone) {
	    if(!VGUtility.isEmpty(personDao.findByPhone(phone)))
            throw new RuntimeException("该号码已被使用,请重新输入!");
		Optional<Person> personOpt = personDao.findById(userId);
		if(personOpt.isPresent()) {
			Person person = personOpt.get();
			person.setPhone(phone);
			return convertToDto(personDao.save(person));
		}
		return null;
	}
	@Override
	public PersonDto changePwd(String userId,String newPwd) {
		Optional<Person> personOpt = personDao.findById(userId);
		if(personOpt.isPresent()) {
			Person person = personOpt.get();
//			if(!person.getPassword().equals(oldPwd))
//				throw new RuntimeException("原始密码输入错误!请重新输入!");
			person.setPassword(newPwd);
			return convertToDto(personDao.save(person));
		}
		return null;
	}
	@Override
	public PersonDto changeSex(String userId, String sex) {
		Optional<Person> personOpt = personDao.findById(userId);
		if(personOpt.isPresent()) {
			Person person = personOpt.get();
			if("0".equals(sex)) {
				person.setGender(IPersonService.GENDER.未知);
			}else if("1".equals(sex)){
				person.setGender(IPersonService.GENDER.男);
			}else if("2".equals(sex)) {
				person.setGender(IPersonService.GENDER.女);
			}
			return convertToDto(personDao.save(person));
		}
		return null;
	}
	@Override
	public PageDto<PersonDto> getPersons(String hql, Map<String, Object> params, PageableDto pageable) {
		return getPersons(hql, "select count(id) " + hql, params, pageable);
	}
	
	@Override
	public PageDto<PersonDto> getPersons(String hql, String countHql, Map<String, Object> params, PageableDto pageable) {
		TypedQuery<Person> query = entityManager.createQuery(hql, Person.class);
		TypedQuery<Long> countQuery = entityManager.createQuery("select count(id) " + hql, Long.class);
		for(Map.Entry<String, Object> entry : params.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
			countQuery.setParameter(entry.getKey(), entry.getValue());
		}
		if (!VGUtility.isEmpty(pageable))
			query.setFirstResult((pageable.getPage() - 1) * pageable.getSize()).setMaxResults(pageable.getSize());
		List<Person> modelList = query.getResultList();
		
		List<PersonDto> resultList = new ArrayList<PersonDto>();
		for (Person model : modelList) {
			PersonDto dto = convertToDto(model);
			String hql1 = "from Message m where 1=1 and m.msgType = 0 and m.personId =:userId";
			Map<String, Object> params1 = new HashMap<String, Object>();
			params1.put("userId", dto.getId());
			PageDto<MessageDto> pageDto = messageService.getMessageByUserId(hql1, params1, null);
			dto.setMessageNum(pageDto.getTotalCount());
			resultList.add(dto);
		}
		return new PageDto<PersonDto>(countQuery.getSingleResult(), resultList);
	}
	@Override
	public void resetPwd(String id, String pwd) {
		Optional<Person>  model = personDao.findById(id);
		if(!VGUtility.isEmpty(model)) {
			Person pModel = model.get();
			pModel.setPassword(pwd);
			personDao.save(pModel);
		}
	}
	@Override
	public PersonDto changeRegisteredIP(String userId, String registeredIP) {
		Optional<Person> personOpt = personDao.findById(userId);
		if(personOpt.isPresent()) {
			Person person = personOpt.get();
			person.setRegisteredIP(registeredIP);;
			return convertToDto(personDao.save(person));
		}
		return null;
	}
	@Override
	public PersonDto changeRecentLoginIP(String userId, String recentLoginIP) {
		Optional<Person> personOpt = personDao.findById(userId);
		if(personOpt.isPresent()) {
			Person person = personOpt.get();
			person.setRecentLoginIP(recentLoginIP);
			return convertToDto(personDao.save(person));
		}
		return null;
	}
	@Override
	public PersonDto changeHead(String userId, String headPortraits) {
		Optional<Person> personOpt = personDao.findById(userId);
		if(personOpt.isPresent()) {
			Person person = personOpt.get();
			person.setHeadPortraits(headPortraits);
			return convertToDto(personDao.save(person));
		}
		return null;
	}
    @Override
    public PersonDto getByPhone(String phone)
    {
        if(!VGUtility.isEmpty(phone)) {
            if(personDao.findByPhone(phone)!=null) {
                return convertToDto(personDao.findByPhone(phone));
            }else {
                return null;
            }
        }else {
            return null;
        }
    }
    @Override
    public PersonDto changeUserName(String userId, String userName)
    {
        if(!VGUtility.isEmpty(personDao.findByUserName(userName))&&!personDao.getById(userId).getUserName().equals(userName))
            throw new RuntimeException("该用户名已被使用,请重新输入!");
        Optional<Person> personOpt = personDao.findById(userId);
        if(personOpt.isPresent()) {
            Person person = personOpt.get();
            person.setUserName(userName);;
            return convertToDto(personDao.save(person));
        }
        return null;
    }
	@Override
	public PersonDto changePhoneForSuEXing(String phone, String personId) {
		Optional<Person> personOpt = personDao.findById(personId);
			if(personOpt.isPresent()) {
				Person person = personOpt.get();
				person.setPhone(phone);
				return convertToDto(personDao.save(person));
			}
			return null;
	}
	
//	@Override
//    public PersonDto changeUpLoadHeadImage(String userId, String upLoadHeadImage) {
//        Optional<Person> personOpt = personDao.findById(userId);
//        if(personOpt.isPresent()) {
//            Person person = personOpt.get();
//            person.setUploadHeadImage(upLoadHeadImage);;
//            return convertToDto(personDao.save(person));
//        }
//        return null;
//    }

	private LoginHistoryDto converter(LoginHistoryModel loginHistoryModel) {
	    LoginHistoryDto loginHistoryDto = new LoginHistoryDto();
	    loginHistoryDto.setId(loginHistoryModel.getId());
	    loginHistoryDto.setUserName(loginHistoryModel.getUserName());
	    loginHistoryDto.setIp(loginHistoryModel.getIp());
	    loginHistoryDto.setStatus(loginHistoryModel.getStatus());
	    loginHistoryDto.setCreateTimestamp(loginHistoryModel.getCreateTimestamp());
	    loginHistoryDto.setLastUpdateTimestamp(loginHistoryModel.getLastUpdateTimestamp());
	    return loginHistoryDto;

	}
	@Override
	public void addLoginHistory(String userName, int status, String ip) {
		LoginHistoryModel loginHistoryModel = new LoginHistoryModel();
		loginHistoryModel.setUserName(userName);
		loginHistoryModel.setStatus(status);
		loginHistoryModel.setIp(ip);
		loginHistoryDao.save(loginHistoryModel);
	}

	@Override
	public List<LoginHistoryDto> getLoginHistoryByQuery(String userName, String beginDate, String endDate, int status, int ifUse) {
		List<LoginHistoryModel> loginHistoryModelByQuery = loginHistoryDao.getLoginHistoryModelByQuery(userName, beginDate, endDate, status, ifUse);
		return loginHistoryModelByQuery.stream().map(o -> converter(o)).collect(Collectors.toList());
	}

	@Override
	public void updateLoginHistoryIfUse(String userName) {
		List<LoginHistoryModel> allByUserNameAndCreateTimestamp = loginHistoryDao.getAllByUserNameAndCreateTimestamp(userName, VGUtility.toDateStr(new Date(), "yyyy-MM-dd"));
		allByUserNameAndCreateTimestamp.stream().forEach(o -> {
			o.setIfUse(1);
			loginHistoryDao.save(o);
		});
	}

	/*
	 * 获取当前天的起始时间
	 */
	private Date getStartTime(Calendar day) {
		day.set(Calendar.HOUR_OF_DAY, 0);
		day.set(Calendar.MINUTE, 0);
		day.set(Calendar.SECOND, 0);
		day.set(Calendar.MILLISECOND, 0);
		return day.getTime();
	}
	/*
	 * 获取当前天的结束时间
	 */
	private Date getEndTime(Calendar day) {
		day.set(Calendar.HOUR_OF_DAY, 23);
		day.set(Calendar.MINUTE, 59);
		day.set(Calendar.SECOND, 59);
		day.set(Calendar.MILLISECOND, 999);
		return day.getTime();
	}

	@Override
	public boolean loginJudge(String userName) {
		Resource res1 = new ClassPathResource("config/file.properties");
		Properties p = new Properties();
		try {
			p.load(res1.getInputStream());
			res1.getInputStream().close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		//获得禁止登录时间
 		int sessionTime=Integer.valueOf(p.getProperty("sessiontime"));

		Date startTime = this.getStartTime(Calendar.getInstance());
		Date endTime = this.getEndTime(Calendar.getInstance());
		String startTimeStr = VGUtility.toDateStr(startTime, "yyyy-MM-dd HH:mm:ss");
		String endTimeStr = VGUtility.toDateStr(endTime, "yyyy-MM-dd HH:mm:ss");
		List<LoginHistoryDto> loginHistoryByQuery = getLoginHistoryByQuery(userName, startTimeStr, endTimeStr, 0, 0);
		if (loginHistoryByQuery.size() == 5) {
			long time = System.currentTimeMillis() - loginHistoryByQuery.get(0).getCreateTimestamp().getTime();
			if (time/1000 >= sessionTime) {
				updateLoginHistoryIfUse(userName);
			} else {
//				throw new InternalAuthenticationServiceException("您已经连续输错五次密码！请" + sessionTime/60 + "分钟后重新登录！");
				return false;
			}
		}
		return true;
	}
}
