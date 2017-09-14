package multibashisample.navneet.in.multibashisample.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sree on 14-09-2017.
 */

public class ItemResponse {

        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("conceptName")
        @Expose
        private String conceptName;
        @SerializedName("pronunciation")
        @Expose
        private String pronunciation;
        @SerializedName("targetScript")
        @Expose
        private String targetScript;
        @SerializedName("audio_url")
        @Expose
        private String audioUrl;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getConceptName() {
            return conceptName;
        }

        public void setConceptName(String conceptName) {
            this.conceptName = conceptName;
        }

        public String getPronunciation() {
            return pronunciation;
        }

        public void setPronunciation(String pronunciation) {
            this.pronunciation = pronunciation;
        }

        public String getTargetScript() {
            return targetScript;
        }

        public void setTargetScript(String targetScript) {
            this.targetScript = targetScript;
        }

        public String getAudioUrl() {
            return audioUrl;
        }

        public void setAudioUrl(String audioUrl) {
            this.audioUrl = audioUrl;
        }
}
