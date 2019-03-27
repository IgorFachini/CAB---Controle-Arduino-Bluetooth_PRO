package com.wickedbotz.cab.model;

/**
 * Created by latitude on 01/03/2017.
 */

public class FollowLineModel {
    private String nome;
    private String vts;
    private String weight1;
    private String weight2;
    private String weight3;
    private String weight4;
    private String proporcinalValue;
    private String max;
    private String tempo;
    private String tempod1;
    private String tempod2;
    private String tempod3;
    private String tempod4;
    private String reta;
    private String maxInverse;

    public  String getStartFollowData(){
        return "[{nome: 'Inicial', " +
                "vts: '200' , " +
                "weight1: '1' , " +
                "weight2: '2' , " +
                "weight3: '6' , " +
                "weight4: '8' , " +
                "maxInverse: '100', " +
                "proporcinalValue: '200', " +
                "max: '200', " +
                "tempo: '60000', " +
                "tempod1: '20000', " +
                "tempod2: '30000', " +
                "tempod3: '40000', " +
                "tempod4: '50000', " +
                "reta: '200'}]";
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getVts() {
        return vts;
    }

    public void setVts(String vts) {
        this.vts = vts;
    }

    public String getWeight1() {
        return weight1;
    }

    public void setWeight1(String weight1) {
        this.weight1 = weight1;
    }

    public String getWeight2() {
        return weight2;
    }

    public void setWeight2(String weight2) {
        this.weight2 = weight2;
    }

    public String getWeight3() {
        return weight3;
    }

    public void setWeight3(String weight3) {
        this.weight3 = weight3;
    }

    public String getWeight4() {
        return weight4;
    }

    public void setWeight4(String weight4) {
        this.weight4 = weight4;
    }

    public String getProporcinalValue() {
        return proporcinalValue;
    }

    public void setProporcinalValue(String proporcinalValue) {
        this.proporcinalValue = proporcinalValue;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }


    public String getReta() {
        return reta;
    }

    public void setReta(String reta) {
        this.reta = reta;
    }

    public String getMaxInverse() {
        return maxInverse;
    }

    public void setMaxInverse(String maxInverse) {
        this.maxInverse = maxInverse;
    }

    public String getTempod1() {
        return tempod1;
    }

    public void setTempod1(String tempod1) {
        this.tempod1 = tempod1;
    }

    public String getTempod2() {
        return tempod2;
    }

    public void setTempod2(String tempod2) {
        this.tempod2 = tempod2;
    }

    public String getTempod3() {
        return tempod3;
    }

    public void setTempod3(String tempod3) {
        this.tempod3 = tempod3;
    }

    public String getTempod4() {
        return tempod4;
    }

    public void setTempod4(String tempod4) {
        this.tempod4 = tempod4;
    }
}
