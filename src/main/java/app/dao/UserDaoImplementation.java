package app.dao;

import app.dao.interfaces.UserDao;
import app.dto.UserDto;

public class UserDaoImplementation implements UserDao {

	@Override
	public UserDto findByUserName(UserDto userDto) throws Exception {
		UserDto validateDto = new UserDto();
		if(userDto.getusername().equals("admin")) {
			validateDto.setusername(userDto.getusername());
			validateDto.setrole(userDto.getusername());
			validateDto.setpassword("admin");
			return validateDto;
		}
		if (userDto.getusername().equals(userDto.getpassword())) {
			validateDto.setusername(userDto.getusername());
			validateDto.setrole(userDto.getusername());
			validateDto.setpassword(userDto.getusername());
			return validateDto;
		}
		return null;
	}

	@Override
	public boolean existsByUserName(UserDto userDto) throws Exception {
		return userDto.getusername().equals("rogelio");
	}

	@Override
	public void createUser(UserDto userDto) throws Exception {
		System.out.println("se ha registrado el usuario");
	}

}

