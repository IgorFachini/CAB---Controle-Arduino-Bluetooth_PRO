package com.example.appbrinquedoopeniot;

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
}
