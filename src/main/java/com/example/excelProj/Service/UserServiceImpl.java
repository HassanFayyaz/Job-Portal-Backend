package com.example.excelProj.Service;

import com.example.excelProj.Model.CandidateProfile;
import com.example.excelProj.Model.CompanyProfile;
import com.example.excelProj.Repository.CandidateProfileRepository;
import com.example.excelProj.Repository.CompanyProfileRepository;
import com.example.excelProj.Repository.UserDaoRepository;
import com.example.excelProj.Commons.ApiResponse;
import com.example.excelProj.Dto.UserDto;
import com.example.excelProj.Model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service("userDetailsService")
public class UserServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserDaoRepository userDaoRepository;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;


	@Autowired
	CompanyProfileRepository companyProfileRepository;

	@Autowired
	private CandidateProfileRepository candidateProfileRepository;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDaoRepository.findByEmail(username);
		if(user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority(user.getUserType()));
	}

	private List<SimpleGrantedAuthority> getAuthority(String role) {
		return Arrays.asList(new SimpleGrantedAuthority(role));
	}

	public List<User> findAll() {
		List<User> list = new ArrayList<>();
		userDaoRepository.findByActive().iterator().forEachRemaining(list::add);
		return list;
	}

	public ApiResponse<List<User>> delete(Long id) {
		Optional<User> userOptional = userDaoRepository.findById(id);
		if(userOptional.isPresent()){
			userDaoRepository.deleteById(id);
		}
		return new ApiResponse<>(HttpStatus.OK.value(), "User saved successfully.",	userDaoRepository.findAll());
	}

	public User findOne(String username) {

		 User user = userDaoRepository.findByEmailAndActive(username,Boolean.TRUE);
		 return user;

	}

	public User findById(Long id) {
		Optional<User> optionalUser = userDaoRepository.findById(id);

		return optionalUser.isPresent() ?  optionalUser.get() : null;
	}

    public UserDto update(UserDto userDto, Long id) {
        User user = findById(id);
        if(user != null) {
            BeanUtils.copyProperties(userDto, user, "password");
            userDaoRepository.save(user);
        }
        return userDto;
    }

    public ApiResponse save(UserDto user) {
		User founduser = userDaoRepository.findByEmail(user.getEmail());
		if(founduser == null) {
			User newUser = new User();

			newUser.setEmail(user.getEmail());
			newUser.setName(user.getName());
			newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
			newUser.setActive(true);
			newUser.setProfileActive(false);
			newUser.setUserType(user.getUserType());
//			userDaoRepository.save(newUser);
//			setting legal company name
			if(user.getLegalCompanyName()!=null){
				CompanyProfile companyProfile = new CompanyProfile();
				companyProfile.setUser(newUser);
				newUser.setName(user.getLegalCompanyName());
				companyProfile.setName(user.getLegalCompanyName());
				companyProfile.setContactName(user.getName());

				companyProfileRepository.save(companyProfile);

			}
			else if(user.getUserType().equalsIgnoreCase("candidate"))
			{
				CandidateProfile candidateProfile=new CandidateProfile();
				candidateProfile.setUser(newUser);
				candidateProfile.setField("");
				candidateProfile.setImageContentType("");
				candidateProfileRepository.save(candidateProfile);

			}
			else if(user.getUserType().equalsIgnoreCase("recruiter") || user.getUserType().equalsIgnoreCase("employer") )
			{
				CompanyProfile companyProfile = new CompanyProfile();
				companyProfile.setUser(newUser);
//				newUser.setName("");
				companyProfile.setName("");
				companyProfile.setContactName("");

				companyProfileRepository.save(companyProfile);
			}

			return new ApiResponse<>(HttpStatus.OK.value(), "User saved successfully.",userDaoRepository.save(newUser));//return ;
		}
		else{
			return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "User Already exsist.",null);//return ;
		}
    }


	public List<User> getActiveUsers(Long id){
		Optional<User> user = userDaoRepository.findById(id);
		if(user.isPresent()){
			User user1 = user.get();
			user1.setActive(false);
			userDaoRepository.save(user1);

			List<User> activeUsers = userDaoRepository.findByActive();
			return activeUsers;
 		}

		return null;
	}


	public  ApiResponse<String> getLoggedInUserImage(Long userId){
        Optional<User> user = userDaoRepository.findById(userId);
       String userType= user.get().getUserType();

        if(user.isPresent()){

            byte [] dp =
                  userType.equalsIgnoreCase("candidate")?
                  user.get().getCandidateProfile().getDp():
                  userType.equalsIgnoreCase("employer")?
                  user.get().getCompanyProfile().getLogo():null;

            return new ApiResponse<String>(200,"Sucessfull",dp.toString());

        }

        return new ApiResponse<String>(500,"failed",null);


    }



	public User saveUser(User user)
	{
		return userDaoRepository.save(user);
	}

}

