package ch03Cleaning;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.IntegerValidator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * Created by Aspire on 21.06.2017.
 */
public class Validation {
    public static void main(String[] args) {
        Validation validation = new Validation();
        validation.validateIntApache("1234");
        validation.validateIntApache("Ishmael");

        String dateFormat = "MM/dd/yyyy";
        validation.validateDate("12/12/1982", dateFormat);
        validation.validateDate("12/12/82", dateFormat);
        validation.validateDate("Ishmael", dateFormat);

        validation.validateEmailApache("myemail@mail.com");
        validation.validateEmailApache("My.Email.123!@mail.net");
        validation.validateEmailApache("myEmail");
        validation.validateEmailApache("myEmail@mail");

        validation.validateZip("12345");
        validation.validateZip("12345-6789");
        validation.validateZip("123");

        validation.validateName("Bobby Smith, Jr.");
        validation.validateName("Bobby Smith the 4th");
        validation.validateName("Albrecht Müller");
        validation.validateName("François Moreau");
    }

    void validateInt(String toValidate){
        try{
            int validInt = Integer.parseInt(toValidate);
            System.out.println(toValidate + " is a valid integer");
        }catch(NumberFormatException e){
            System.out.println(toValidate + " is not a valid integer");
        }
    }

    void validateIntApache(String toValidate){
        IntegerValidator intValidator = IntegerValidator.getInstance();
        if(intValidator.isValid(toValidate)){
            System.out.println(toValidate + " is a valid integer");
        }else{
            System.out.println(toValidate + " is not a valid integer");
        }
    }

    void validateDate(String theDate, String dateFormat){
        try{
            SimpleDateFormat format = new SimpleDateFormat(dateFormat);
            Date test = format.parse(theDate);
            if(format.format(test).equals(theDate)){
                System.out.println(theDate + " is a valid date");
            }else{
                System.out.println(theDate + " is not a valid date");
            }
        }catch(ParseException e){
            System.out.println(theDate + " is not a valid date");
        }
    }

    void validateEmail(String email){
        String emailRegex = "^[a-zA-Z0-9.!$'*+/=?^_`{|}~-" +
                "]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\." +
                "[0-9]{1,3}\\])|(([a-zAZ\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        if(matcher.matches()){
            System.out.println(email + " is a valid email address");
        }else{
            System.out.println(email + " is not a valid email address");
        }
    }

    void validateEmailJse(String email){
        try {
            InternetAddress testEmail = new InternetAddress(email);
            testEmail.validate();
            System.out.println(email + " is a valid email address");
        } catch (AddressException e) {
            System.out.println(email + " is not a valid email address");
        }

    }

    void validateEmailApache(String email){
        email = email.trim();
        EmailValidator eValidator = EmailValidator.getInstance();
        if(eValidator.isValid(email)){
            System.out.println(email + " is a valid email address");
        }else{
            System.out.println(email + " is not a valid email address");
        }
    }

    void validateZip(String zip){
        String zipRegex = "^[0-9]{5}(?:-[0-9]{4})?$";
        Pattern pattern = Pattern.compile(zipRegex);
        Matcher matcher = pattern.matcher(zip);
        if(matcher.matches()){
            System.out.println(zip + " is a valid zip code");
        }else{
            System.out.println(zip + " is not a valid zip code");
        }
    }

    void validateName(String name){
        String nameRegex = "^[\\p{L}\\s-',\\.]+$";
        Pattern pattern = Pattern.compile(nameRegex);
        Matcher matcher = pattern.matcher(name);
        if(matcher.matches()){
            System.out.println(name + " is a valid name");
        }else{
            System.out.println(name + " is not a valid name");
        }
    }
}
