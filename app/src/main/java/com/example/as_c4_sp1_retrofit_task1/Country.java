package com.example.as_c4_sp1_retrofit_task1;

import java.util.Map;

public class Country
{
        private String name;
        private String population;
        private Map<String, String> flags;

        public Country(String name, String population,Map<String, String> flags)
        {
            this.name=name;
            this.population=population;
            this.flags = flags;
        }

        public String getNameCountry() {
            return this.name;
        }

        public void setNameCountry(String name) {
            this.name = name;
        }

        public String getPopulationCountry() {
            return this.population;
        }

        public void setPopulationCountry(String population) {
            this.population = population;
        }

    public Map<String, String> getFlags() {
        return flags;
    }

    public void setFlags(Map<String, String> flags) {
        this.flags = flags;
    }
}
