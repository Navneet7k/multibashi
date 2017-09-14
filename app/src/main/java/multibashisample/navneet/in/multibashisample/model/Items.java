package multibashisample.navneet.in.multibashisample.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Sree on 14-09-2017.
 */

public class Items {
        @SerializedName("lesson_data")
        @Expose
        private List<ItemResponse> lessonData = null;

        public List<ItemResponse> getLessonData() {
            return lessonData;
        }

        public void setLessonData(List<ItemResponse> lessonData) {
            this.lessonData = lessonData;
        }
}
