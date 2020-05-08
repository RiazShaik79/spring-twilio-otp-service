package io.javabrains;

import java.util.HashMap;
import java.util.Map;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;
import com.twilio.rest.api.v2010.account.Message;

@RestController
@RefreshScope
public class HomeResource {
	
	private Map<String, OTPModel> otp_data = new HashMap<>(); 
	private final static String ACCOUNT_ID= "ACa3b68c5619f477593bfd412097a4a96c";
	private final static String AUTH_ID="d140622ef2c9343d4d2ca159a6b69317";
	
	static {
		Twilio.init(ACCOUNT_ID, AUTH_ID);
	}
	
/*	@RequestMapping(value="/mobilenumbers/{mobilenumber}/otp", method=RequestMethod.POST)
	public ResponseEntity<Object> sendOTP(@PathVariable("mobilenumber") String mobilenumber) {
		
		OTPModel otpModel = new OTPModel();
		otpModel.setMobilenumber(mobilenumber);
		otpModel.setOtp(String.valueOf(((int) (Math.random()*(10000 - 1000)))+1000));
		otpModel.setExpirytime(System.currentTimeMillis()*30000);
		
		otp_data.put(mobilenumber, otpModel) ;
		Message.creator(new PhoneNumber("+4407492274395"), new PhoneNumber("+12057497708"), "Your OTP is " + otpModel.getOtp()).create();
		
		return new ResponseEntity<>("OTP is sent successfully",HttpStatus.OK);
	
	} */
	
	@RequestMapping(value="/sendOTP", method=RequestMethod.POST)
	public OTPModel sendOTP(@RequestBody OTPModel otpRequest) {
		
		System.out.println(otpRequest.getMobilenumber());
		OTPModel otpModel = new OTPModel();
		otpModel.setMobilenumber(otpRequest.getMobilenumber());
		otpModel.setOtp(String.valueOf(((int) (Math.random()*(10000 - 1000)))+1000));
		otpModel.setExpirytime(System.currentTimeMillis()*30000);
		
		otp_data.put(otpRequest.getMobilenumber(), otpModel) ;
		//Message.creator(new PhoneNumber("+4407492274395"), new PhoneNumber("+12057497708"), "Your OTP is " + otpModel.getOtp()).create();
		
		return otpModel;
		//return new ResponseEntity<>("OTP123 is sent successfully",HttpStatus.OK);
	}
	
/*	@RequestMapping(value="/mobilenumbers/{mobilenumber}/otp", method=RequestMethod.PUT)
	public ResponseEntity<Object> verifyOTP(@PathVariable("mobilenumber") String mobilenumber, @RequestBody OTPModel requestBodyotpModel) {
		
		if (requestBodyotpModel.getOtp()==null || requestBodyotpModel.getOtp().trim().length()<0) {
			return new ResponseEntity<>("Please provide OTP",HttpStatus.BAD_REQUEST);
		}
		
		
		if (otp_data.containsKey(mobilenumber)) {
			OTPModel otpModel = otp_data.get(mobilenumber);
			if (otpModel!=null) {
				if (otpModel.getExpirytime()>=System.currentTimeMillis()) {
					if (requestBodyotpModel.getOtp().equals(otpModel.getOtp())) {
						otp_data.remove(mobilenumber);
						return new ResponseEntity<>("OTP is verified successfully",HttpStatus.OK);
					}
					return new ResponseEntity<>("Invalid OTP",HttpStatus.BAD_REQUEST);
				}
				return new ResponseEntity<>("OTP is expired",HttpStatus.BAD_REQUEST);
				
			}
			return new ResponseEntity<>("Something went wrong..!!",HttpStatus.BAD_REQUEST);
			
		}
		return new ResponseEntity<>("Mobile number not found",HttpStatus.NOT_FOUND);
		
	} */
	
	@RequestMapping(value="/verifyOTP", method=RequestMethod.POST)
	public OTPModel verifyOTP(@RequestBody OTPModel requestBodyotpModel) {
		
		OTPModel otpModel = new OTPModel();
		
		if (requestBodyotpModel.getOtp()==null || requestBodyotpModel.getOtp().trim().length()<0) {
			otpModel.setStatus("Please provide OTP");
			return otpModel;
		}
		
		
		if (otp_data.containsKey(requestBodyotpModel.getMobilenumber())) {
			otpModel = otp_data.get(requestBodyotpModel.getMobilenumber());
			if (otpModel!=null) {
				if (otpModel.getExpirytime()>=System.currentTimeMillis()) {
					if (requestBodyotpModel.getOtp().equals(otpModel.getOtp())) {
						otp_data.remove(requestBodyotpModel.getMobilenumber());
						otpModel.setStatus("OTP is verified successfully");
						return otpModel;
						
					}
					otpModel.setStatus("Invalid OTP");
					return otpModel;
					
				}
				otpModel.setStatus("OTP is expired");
				return otpModel;
				
				
			}
			otpModel.setStatus("Something went wrong..!!");
			return otpModel;
			
			
			
		}
		otpModel.setStatus("Mobile number not found");		
		return otpModel;
		
	}
	
	



}
