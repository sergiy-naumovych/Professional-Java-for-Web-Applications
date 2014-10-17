package com.wrox.site;

import com.wrox.config.annotation.WebController;
import com.wrox.site.entities.Address;
import com.wrox.site.entities.Applicant;
import com.wrox.site.entities.Employee;
import com.wrox.site.entities.NewsArticle;
import com.wrox.site.entities.Person;
import com.wrox.site.entities.PhoneNumber;
import com.wrox.site.entities.PostalCode;
import com.wrox.site.entities.Resume;
import com.wrox.site.entities.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.inject.Inject;
import java.util.Map;

@WebController
public class MainController
{
    @Inject EverythingService service;

    @RequestMapping({"", "/"})
    public String home()
    {
        return "home";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String listUsers(Map<String, Object> model)
    {
        model.put("users", this.service.getAllUsers());

        return "list-users";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public View addUser(@RequestParam("username") String username)
    {
        User user = new User();
        user.setUsername(username);

        this.service.saveUser(user);

        return new RedirectView("/user", true, false);
    }

    @RequestMapping(value = "/person", method = RequestMethod.GET)
    public String listPersons(Map<String, Object> model)
    {
        model.put("personForm", new PersonForm());
        model.put("persons", this.service.getAllPersons());

        return "list-persons";
    }

    @RequestMapping(value = "/person", method = RequestMethod.POST)
    public View addPerson(PersonForm form)
    {
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setCountryCode(form.getPhoneCountryCode());
        phoneNumber.setNumber(form.getPhoneNumber());

        PostalCode postalCode = new PostalCode();
        postalCode.setCode(form.getPostalCode());
        postalCode.setSuffix(form.getPostalCodeSuffix());

        Address address = new Address();
        address.setStreet(form.getAddressStreet());
        address.setCity(form.getAddressCity());
        address.setState(form.getAddressState());
        address.setCountry(form.getAddressCountry());
        address.setPostalCode(postalCode);

        Person person = new Person();
        person.setFirstName(form.getFirstName());
        person.setLastName(form.getLastName());
        person.setPhoneNumber(phoneNumber);
        person.setAddress(address);

        this.service.savePerson(person);

        return new RedirectView("/person", true, false);
    }

    @RequestMapping(value = "/applicant", method = RequestMethod.GET)
    public String listApplicants(Map<String, Object> model)
    {
        model.put("applicantForm", new ApplicantForm());
        model.put("résuméForm", new RésuméForm());
        model.put("applicants", this.service.getAllApplicants());

        return "list-applicants";
    }

    @RequestMapping(value = "/applicant", method = RequestMethod.POST)
    public View addApplicant(ApplicantForm form)
    {
        Applicant applicant = new Applicant();
        applicant.setFirstName(form.getFirstName());
        applicant.setLastName(form.getLastName());
        applicant.setCitizen(form.isCitizen());

        this.service.saveApplicant(applicant);

        return new RedirectView("/applicant", true, false);
    }

    @RequestMapping(value = "/applicant/resume", method = RequestMethod.POST)
    public View addRésumé(RésuméForm form)
    {
        Applicant applicant = this.service.getApplicant(form.getApplicantId());

        Resume résumé = new Resume();
        résumé.setApplicant(applicant);
        résumé.setTitle(form.getTitle());
        résumé.setContent(form.getContent());

        this.service.saveRésumé(résumé);

        return new RedirectView("/applicant", true, false);
    }

    @RequestMapping(value = "/news", method = RequestMethod.GET)
    public String listArticles(Map<String, Object> model)
    {
        model.put("newsArticleForm", new NewsArticleForm());
        model.put("articles", this.service.getAllNewsArticles());

        return "list-articles";
    }

    @RequestMapping(value = "/news", method = RequestMethod.POST)
    public View addArticle(NewsArticleForm form)
    {
        NewsArticle article = new NewsArticle();
        article.setTitle(form.getTitle());
        article.setContent(form.getContent());

        this.service.saveNewsArticle(article);

        return new RedirectView("/news", true, false);
    }

    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    public String listEmployees(Map<String, Object> model)
    {
        model.put("employeeForm", new EmployeeForm());
        model.put("addressForm", new AddressForm());
        model.put("employees", this.service.getAllEmployees());

        return "list-employees";
    }

    @RequestMapping(value = "/employee", method = RequestMethod.POST)
    public View addEmployee(EmployeeForm form)
    {
        Employee employee = new Employee();
        employee.setFirstName(form.getFirstName());
        employee.setLastName(form.getLastName());

        this.service.saveEmployee(employee);

        return new RedirectView("/employee", true, false);
    }

    @RequestMapping(value = "/employee/phone", method = RequestMethod.POST)
    public View addPhoneNumber(@RequestParam("employeeId") long employeeId,
                               @RequestParam("number") String number)
    {
        Employee employee = this.service.getEmployee(employeeId);
        employee.getPhoneNumbers().add(number);
        this.service.saveEmployee(employee);

        return new RedirectView("/employee", true, false);
    }

    @RequestMapping(value = "/employee/address", method = RequestMethod.POST)
    public View addAddress(AddressForm form)
    {
        PostalCode postalCode = new PostalCode();
        postalCode.setCode(form.getPostalCode());
        postalCode.setSuffix(form.getPostalCodeSuffix());

        Address address = new Address();
        address.setStreet(form.getStreet());
        address.setCity(form.getCity());
        address.setState(form.getState());
        address.setCountry(form.getCountry());
        address.setPostalCode(postalCode);

        Employee employee = this.service.getEmployee(form.getEmployeeId());
        employee.getAddresses().add(address);
        this.service.saveEmployee(employee);

        return new RedirectView("/employee", true, false);
    }

    @RequestMapping(value = "/employee/property", method = RequestMethod.POST)
    public View setProperty(@RequestParam("employeeId") long employeeId,
                            @RequestParam("name") String name,
                            @RequestParam("value") String value)
    {
        Employee employee = this.service.getEmployee(employeeId);
        employee.getExtraProperties().put(name, value);
        this.service.saveEmployee(employee);

        return new RedirectView("/employee", true, false);
    }

    public static class PersonForm
    {
        private long id;
        private String firstName;
        private String lastName;
        private String phoneCountryCode;
        private String phoneNumber;
        private String addressStreet;
        private String addressCity;
        private String addressState;
        private String addressCountry;
        private String postalCode;
        private String postalCodeSuffix;

        public long getId()
        {
            return id;
        }

        public void setId(long id)
        {
            this.id = id;
        }

        public String getFirstName()
        {
            return firstName;
        }

        public void setFirstName(String firstName)
        {
            this.firstName = firstName;
        }

        public String getLastName()
        {
            return lastName;
        }

        public void setLastName(String lastName)
        {
            this.lastName = lastName;
        }

        public String getPhoneCountryCode()
        {
            return phoneCountryCode;
        }

        public void setPhoneCountryCode(String phoneCountryCode)
        {
            this.phoneCountryCode = phoneCountryCode;
        }

        public String getPhoneNumber()
        {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber)
        {
            this.phoneNumber = phoneNumber;
        }

        public String getAddressStreet()
        {
            return addressStreet;
        }

        public void setAddressStreet(String addressStreet)
        {
            this.addressStreet = addressStreet;
        }

        public String getAddressCity()
        {
            return addressCity;
        }

        public void setAddressCity(String addressCity)
        {
            this.addressCity = addressCity;
        }

        public String getAddressState()
        {
            return addressState;
        }

        public void setAddressState(String addressState)
        {
            this.addressState = addressState;
        }

        public String getAddressCountry()
        {
            return addressCountry;
        }

        public void setAddressCountry(String addressCountry)
        {
            this.addressCountry = addressCountry;
        }

        public String getPostalCode()
        {
            return postalCode;
        }

        public void setPostalCode(String postalCode)
        {
            this.postalCode = postalCode;
        }

        public String getPostalCodeSuffix()
        {
            return postalCodeSuffix;
        }

        public void setPostalCodeSuffix(String postalCodeSuffix)
        {
            this.postalCodeSuffix = postalCodeSuffix;
        }
    }

    public static class ApplicantForm
    {
        private String firstName;
        private String lastName;
        private boolean citizen;

        public String getFirstName()
        {
            return firstName;
        }

        public void setFirstName(String firstName)
        {
            this.firstName = firstName;
        }

        public String getLastName()
        {
            return lastName;
        }

        public void setLastName(String lastName)
        {
            this.lastName = lastName;
        }

        public boolean isCitizen()
        {
            return citizen;
        }

        public void setCitizen(boolean citizen)
        {
            this.citizen = citizen;
        }
    }

    public static class RésuméForm
    {
        private long applicantId;
        private String title;
        private String content;

        public long getApplicantId()
        {
            return applicantId;
        }

        public void setApplicantId(long applicantId)
        {
            this.applicantId = applicantId;
        }

        public String getTitle()
        {
            return title;
        }

        public void setTitle(String title)
        {
            this.title = title;
        }

        public String getContent()
        {
            return content;
        }

        public void setContent(String content)
        {
            this.content = content;
        }
    }

    public static class NewsArticleForm
    {
        private String title;
        private String content;

        public String getTitle()
        {
            return this.title;
        }

        public void setTitle(String title)
        {
            this.title = title;
        }

        public String getContent()
        {
            return this.content;
        }

        public void setContent(String content)
        {
            this.content = content;
        }
    }

    public static class EmployeeForm
    {
        private String firstName;
        private String lastName;

        public String getFirstName()
        {
            return firstName;
        }

        public void setFirstName(String firstName)
        {
            this.firstName = firstName;
        }

        public String getLastName()
        {
            return lastName;
        }

        public void setLastName(String lastName)
        {
            this.lastName = lastName;
        }
    }

    public static class AddressForm
    {
        private long employeeId;
        private String street;
        private String city;
        private String state;
        private String country;
        private String postalCode;
        private String postalCodeSuffix;

        public long getEmployeeId()
        {
            return employeeId;
        }

        public void setEmployeeId(long employeeId)
        {
            this.employeeId = employeeId;
        }

        public String getStreet()
        {
            return street;
        }

        public void setStreet(String street)
        {
            this.street = street;
        }

        public String getCity()
        {
            return city;
        }

        public void setCity(String city)
        {
            this.city = city;
        }

        public String getState()
        {
            return state;
        }

        public void setState(String state)
        {
            this.state = state;
        }

        public String getCountry()
        {
            return country;
        }

        public void setCountry(String country)
        {
            this.country = country;
        }

        public String getPostalCode()
        {
            return postalCode;
        }

        public void setPostalCode(String postalCode)
        {
            this.postalCode = postalCode;
        }

        public String getPostalCodeSuffix()
        {
            return postalCodeSuffix;
        }

        public void setPostalCodeSuffix(String postalCodeSuffix)
        {
            this.postalCodeSuffix = postalCodeSuffix;
        }
    }
}
