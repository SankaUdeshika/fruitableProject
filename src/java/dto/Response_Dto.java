package dto;

import com.google.gson.annotations.Expose;
import java.io.Serializable;

public class Response_Dto implements Serializable {

    public Response_Dto(){
        
    }
    
    
    @Expose
    private boolean success;
    
    @Expose
    private Object content;

    /**
     * @return the success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @param success the success to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * @return the content
     */
    public Object getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(Object content) {
        this.content = content;
    }
}
