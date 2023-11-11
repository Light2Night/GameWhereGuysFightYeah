package ViewModels.Recruit;

import org.jetbrains.annotations.Nullable;
import properties.user.recruit.RecruitCost;

public class RecruitViewModel {
    public final int Id;
    public final int CharID;
    public final String Name;
    public final String Description;
    public final int Stars;
    public final int Level;
    public final String ProfileImage;
    public final BaseRecruitViewModel Data;
    @Nullable
    public final RecruitCost Cost;

    public RecruitViewModel(int id, int charID, String name, String description, int stars, int level, String profileImage, BaseRecruitViewModel data, @Nullable RecruitCost cost) {
        Id = id;
        CharID = charID;
        Name = name;
        Description = description;
        Stars = stars;
        Level = level;
        ProfileImage = profileImage;
        Data = data;
        Cost = cost;
    }
}
