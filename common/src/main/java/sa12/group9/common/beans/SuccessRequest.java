package sa12.group9.common.beans;

public class SuccessRequest extends SearchIssueRequest
{
    private FoundInformation information;

    public FoundInformation getInformation()
    {
        return information;
    }

    public void setInformation(FoundInformation information)
    {
        this.information = information;
    }
}
