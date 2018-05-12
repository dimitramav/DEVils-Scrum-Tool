package ys09.auth;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.lang.String;

public class AuthScope {

    Map<String, ArrayList> projects_per_developer = new HashMap<String,ArrayList>();
    Map<String, ArrayList> projects_per_owner = new HashMap<String,ArrayList>();
    List<Map<String, ArrayList>> final_scope = new ArrayList<Map<String, ArrayList>>();

    public AuthScope(){
        //connect a developer with his projects : "developer":[1,2,4]
        ArrayList<Integer> developer_list = new ArrayList<Integer>();
        developer_list.add(1);
        developer_list.add(2);
        projects_per_developer.put("developer",developer_list);
        final_scope.add(projects_per_developer);

        ArrayList<Integer> owner_list = new ArrayList<Integer>();
        owner_list.add(3);
        owner_list.add(4);
        projects_per_owner.put("owner",owner_list);

        final_scope.add(projects_per_owner);

    }

    public AuthScope add_as_owner(int project_id)
    {
        return this;
    }

    public AuthScope add_as_developer(int project_id)
    {
        return this;
    }

}
