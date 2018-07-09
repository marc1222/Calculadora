package calculadora.com.calculadora;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Puntuation extends RealmObject {
        private Date date;
        private short grade;
        public Puntuation() {

        }
        public Puntuation(Date d, short p) {
            this.date = d;
            this.grade = p;
        }
        public short getPuntutation() {
            return grade;
        }
       public Date getdate() {
            return date;
        }
        public void setPuntutation(short p) {
            this.grade = p;
        }
        public void setdate(Date p) {
            this.date = p;
        }

}
