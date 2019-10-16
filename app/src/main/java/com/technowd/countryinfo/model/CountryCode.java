package com.technowd.countryinfo.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by salem Aljebaly on 10/20/2018.
 */
public class CountryCode implements Serializable {
    private String name;
    private List<String> topLevelDomain;
    private String alpha2Code;
    private String alpha3Code;
    private List<String> callingCodes;
    private String capital;
    private List<String> altSpellings;
    private String region;
    private String subregion;
    private int population;
    private List<Double> latlng;
    private String demonym;
    private Double area;
    private Object gini;
    private List<String> timezones;
    private List<String> borders;
    private String nativeName;
    private String numericCode;
    private List<Currencies> currencies;
    private List<Language> languages;
    private Translations translations;
    private String flag;
    private List<RegionalBlocs> regionalBlocs;
    private String cioc;

    public String getName() {
        return name;
    }

    public List<String> getTopLevelDomain() {
        return topLevelDomain;
    }

    public String getAlpha2Code() {
        return alpha2Code;
    }

    public String getAlpha3Code() {
        return alpha3Code;
    }

    public List<String> getCallingCodes() {
        return callingCodes;
    }

    public String getCapital() {
        return capital;
    }

    public List<String> getAltSpellings() {
        return altSpellings;
    }

    public String getRegion() {
        return region;
    }

    public String getSubregion() {
        return subregion;
    }

    public int getPopulation() {
        return population;
    }

    public List<Double> getLatlng() {
        return latlng;
    }

    public String getDemonym() {
        return demonym;
    }

    public Double getArea() {
        return area;
    }

    public Object getGini() {
        return gini;
    }

    public List<String> getTimezones() {
        return timezones;
    }

    public List<String> getBorders() {
        return borders;
    }

    public String getNativeName() {
        return nativeName;
    }

    public String getNumericCode() {
        return numericCode;
    }

    public List<Currencies> getCurrencies() {
        return currencies;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public Translations getTranslations() {
        return translations;
    }

    public String getFlag() {
        return flag;
    }

    public List<RegionalBlocs> getRegionalBlocs() {
        return regionalBlocs;
    }

    public String getCioc() {
        return cioc;
    }

    public CountryCode(String name, List<String> topLevelDomain, String alpha2Code, String alpha3Code, List<String> callingCodes, String capital, List<String> altSpellings, String region, String subregion, int population, List<Double> latlng, String demonym, Double area, Object gini, List<String> timezones, List<String> borders, String nativeName, String numericCode, List<Currencies> currencies, List<Language> languages, Translations translations, String flag, String cioc) {
        this.name = name;
        this.topLevelDomain = topLevelDomain;
        this.alpha2Code = alpha2Code;
        this.alpha3Code = alpha3Code;
        this.callingCodes = callingCodes;
        this.capital = capital;
        this.altSpellings = altSpellings;
        this.region = region;
        this.subregion = subregion;
        this.population = population;
        this.latlng = latlng;
        this.demonym = demonym;
        this.area = area;
        this.gini = gini;
        this.timezones = timezones;
        this.borders = borders;
        this.nativeName = nativeName;
        this.numericCode = numericCode;
        this.currencies = currencies;
        this.languages = languages;
        this.translations = translations;
        this.flag = flag;
        this.cioc = cioc;
    }

    public class Currencies {
        private String code;
        private String name;
        private String symbol;

        public Currencies(String code, String name, String symbol) {
            this.code = code;
            this.name = name;
            this.symbol = symbol;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public String getSymbol() {
            return symbol;
        }
    }

    private class Language {
        private String iso639_1;
        private String iso639_2;
        private String name;
        private String nativeName;

        public Language(String iso639_1, String iso639_2, String name, String nativeName) {
            this.iso639_1 = iso639_1;
            this.iso639_2 = iso639_2;
            this.name = name;
            this.nativeName = nativeName;
        }

        public String getIso639_1() {
            return iso639_1;
        }

        public String getIso639_2() {
            return iso639_2;
        }

        public String getName() {
            return name;
        }

        public String getNativeName() {
            return nativeName;
        }
    }

    private class Translations {
        private String de,es,fr,ja,it,br,pt,nl,hr,fa;

        public Translations(String de, String es, String fr, String ja, String it, String br, String pt, String nl, String hr, String fa) {
            this.de = de;
            this.es = es;
            this.fr = fr;
            this.ja = ja;
            this.it = it;
            this.br = br;
            this.pt = pt;
            this.nl = nl;
            this.hr = hr;
            this.fa = fa;
        }

        public String getDe() {
            return de;
        }

        public String getEs() {
            return es;
        }

        public String getFr() {
            return fr;
        }

        public String getJa() {
            return ja;
        }

        public String getIt() {
            return it;
        }

        public String getBr() {
            return br;
        }

        public String getPt() {
            return pt;
        }

        public String getNl() {
            return nl;
        }

        public String getHr() {
            return hr;
        }

        public String getFa() {
            return fa;
        }
    }

    public class RegionalBlocs {
        private String acronym;
        private String name;
        private List<String> otherAcronyms;
        private List<String> otherNames;

        public String getAcronym() {
            return acronym;
        }

        public String getName() {
            return name;
        }

        public List<String> getOtherAcronyms() {
            return otherAcronyms;
        }

        public List<String> getOtherNames() {
            return otherNames;
        }
    }
}
