package com.wrox.site;

import com.wrox.config.annotation.WebController;
import com.wrox.site.entities.Gender;
import com.wrox.site.entities.Person;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.inject.Inject;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

@WebController
public class MainController
{
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @Inject PersonService personService;

    @RequestMapping("/")
    public View home()
    {
        return new RedirectView("/people/add", true, false);
    }

    @RequestMapping(value = "/people/add", method = RequestMethod.GET)
    public String add(Map<String, Object> model)
    {
        model.put("added", null);
        model.put("personForm", new PersonForm());
        return "people/add";
    }

    @RequestMapping(value = "/people/add", method = RequestMethod.POST)
    public String add(Map<String, Object> model, PersonForm form) throws ParseException
    {
        Person person = new Person();
        person.setFirstName(form.getFirstName());
        person.setMiddleInitial(form.getMiddleInitial());
        person.setLastName(form.getLastName());
        person.setState(form.getState());
        person.setCountry(form.getCountry());
        person.setBirthDate(new Date(
                this.formatter.parse(form.getBirthDate()).getTime()
        ));
        person.setGender(form.getGender());
        person.setRace(form.getRace());
        person.setEthnicity(form.getEthnicity());

        this.personService.savePerson(person);

        model.put("added", person.getId());
        model.put("personForm", new PersonForm());
        return "people/add";
    }

    @RequestMapping(value = "/people/find", method = RequestMethod.GET)
    public String find(Map<String, Object> model)
    {
        model.put("searchForm", new SearchForm());
        model.put("results", null);
        return "people/find";
    }

    @RequestMapping(value = "/people/find", params = "search=true",
            method = { RequestMethod.GET, RequestMethod.POST })
    private String find(Map<String, Object> model, SearchForm form,
                        Pageable pageable) throws ParseException
    {
        SearchCriteria criteria = SearchCriteria.Builder.create();
        if(form.isIncludeFirstName())
            criteria.add(new Criterion("firstName", Criterion.Operator.EQ, form.getFirstName()));
        if(form.isIncludeMiddleInitial())
            criteria.add(new Criterion("middleInitial", Criterion.Operator.EQ, form.getMiddleInitial()));
        if(form.isIncludeLastName())
            criteria.add(new Criterion("lastName", Criterion.Operator.EQ, form.getLastName()));
        if(form.isIncludeState())
            criteria.add(new Criterion("state", Criterion.Operator.EQ, form.getState()));
        if(form.isIncludeCountry())
            criteria.add(new Criterion("country", Criterion.Operator.EQ, form.getCountry()));
        if(form.isIncludeBirthDate())
            criteria.add(new Criterion("birthDate", Criterion.Operator.EQ, new Date(
                    this.formatter.parse(form.getBirthDate()).getTime()
            )));
        if(form.isIncludeGender())
            criteria.add(new Criterion("gender", Criterion.Operator.EQ, form.getGender()));
        if(form.isIncludeRace())
            criteria.add(new Criterion("race", Criterion.Operator.EQ, form.getRace()));
        if(form.isIncludeEthnicity())
            criteria.add(new Criterion("ethnicity", Criterion.Operator.EQ, form.getEthnicity()));

        model.put("searchForm", form);
        model.put("results", this.personService.searchPeople(criteria, pageable));
        return "people/find";
    }

    public static class PersonForm
    {
        private String firstName;
        private String middleInitial;
        private String lastName;
        private String state;
        private String country;
        private String birthDate;
        private Gender gender;
        private String race;
        private String ethnicity;

        public String getFirstName()
        {
            return firstName;
        }

        public void setFirstName(String firstName)
        {
            this.firstName = firstName;
        }

        public String getMiddleInitial()
        {
            return middleInitial;
        }

        public void setMiddleInitial(String middleInitial)
        {
            this.middleInitial = middleInitial;
        }

        public String getLastName()
        {
            return lastName;
        }

        public void setLastName(String lastName)
        {
            this.lastName = lastName;
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

        public String getBirthDate()
        {
            return birthDate;
        }

        public void setBirthDate(String birthDate)
        {
            this.birthDate = birthDate;
        }

        public Gender getGender()
        {
            return gender;
        }

        public void setGender(Gender gender)
        {
            this.gender = gender;
        }

        public String getRace()
        {
            return race;
        }

        public void setRace(String race)
        {
            this.race = race;
        }

        public String getEthnicity()
        {
            return ethnicity;
        }

        public void setEthnicity(String ethnicity)
        {
            this.ethnicity = ethnicity;
        }
    }

    public static class SearchForm extends PersonForm
    {
        private boolean includeFirstName;
        private boolean includeMiddleInitial;
        private boolean includeLastName;
        private boolean includeState;
        private boolean includeCountry;
        private boolean includeBirthDate;
        private boolean includeGender;
        private boolean includeRace;
        private boolean includeEthnicity;

        public boolean isIncludeFirstName()
        {
            return includeFirstName;
        }

        public void setIncludeFirstName(boolean includeFirstName)
        {
            this.includeFirstName = includeFirstName;
        }

        public boolean isIncludeMiddleInitial()
        {
            return includeMiddleInitial;
        }

        public void setIncludeMiddleInitial(boolean includeMiddleInitial)
        {
            this.includeMiddleInitial = includeMiddleInitial;
        }

        public boolean isIncludeLastName()
        {
            return includeLastName;
        }

        public void setIncludeLastName(boolean includeLastName)
        {
            this.includeLastName = includeLastName;
        }

        public boolean isIncludeState()
        {
            return includeState;
        }

        public void setIncludeState(boolean includeState)
        {
            this.includeState = includeState;
        }

        public boolean isIncludeCountry()
        {
            return includeCountry;
        }

        public void setIncludeCountry(boolean includeCountry)
        {
            this.includeCountry = includeCountry;
        }

        public boolean isIncludeBirthDate()
        {
            return includeBirthDate;
        }

        public void setIncludeBirthDate(boolean includeBirthDate)
        {
            this.includeBirthDate = includeBirthDate;
        }

        public boolean isIncludeGender()
        {
            return includeGender;
        }

        public void setIncludeGender(boolean includeGender)
        {
            this.includeGender = includeGender;
        }

        public boolean isIncludeRace()
        {
            return includeRace;
        }

        public void setIncludeRace(boolean includeRace)
        {
            this.includeRace = includeRace;
        }

        public boolean isIncludeEthnicity()
        {
            return includeEthnicity;
        }

        public void setIncludeEthnicity(boolean includeEthnicity)
        {
            this.includeEthnicity = includeEthnicity;
        }
    }
}
