package korenski.service.sifrarnici;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import korenski.model.sifrarnici.Activity;
import korenski.repository.sifrarnici.ActivityRepository;

@Service
public class ActivityService {
	@Autowired
	ActivityRepository activityRepository;
	
	
	public Activity create(Activity newActivity){
		return activityRepository.save(newActivity);
	}
	
	public Activity edit(Activity activity){
		return activityRepository.save(activity);
	}
	
	public Activity findActivity(Long id){
		return activityRepository.findOne(id);
	}
	
	public void deleteActivity(Long id){
		activityRepository.delete(id);
	}
	
	public Activity findActivityByCode(String code){
		return activityRepository.findActivityByCode(code);
	}
	
	public Activity findActivityByName(String name){
		return activityRepository.findActivityByName(name);
	}
	
	public Set<Activity> findAll(){
		return activityRepository.findAll();
	}
	
	public Set<Activity> findActivityByCodeAndName(String code,String name){
		return activityRepository.findByCodeContainingIgnoreCaseOrNameContainingIgnoreCase(code, name);
	}
}
