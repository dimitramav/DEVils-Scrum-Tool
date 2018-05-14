package ys09.auth;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.lang.String;

public class AuthScope {

    Map<String, ArrayList> projects_per_developer = new HashMap<String,ArrayList>();
    Map<String, ArrayList> projects_per_owner = new HashMap<String,ArrayList>();
    Map<String, ArrayList> projects_per_master = new HashMap<String,ArrayList>();
    List<Map<String, ArrayList>> final_scope = new ArrayList<Map<String, ArrayList>>();

    public AuthScope(List<Integer> devProjectsID,List<Integer> ownerProjectsID ,List<Integer> scrumProjectsID){
        //connect a developer with his projects : "developer":[1,2,4]

        projects_per_developer.put("developer",(ArrayList) devProjectsID);
        final_scope.add(projects_per_developer);

        //connect an owner with his projects : "product_owner":[1,2,4]
        projects_per_owner.put("product_owner", (ArrayList) ownerProjectsID);

        final_scope.add(projects_per_owner);

        //connect a scrum master with his projects : "srum_master":[1,2,4]
        projects_per_master.put("scrum_master", (ArrayList) scrumProjectsID);

        final_scope.add(projects_per_master);

    }

    public AuthScope add_authorized_project(int proj_id, String role)
    {
        for (Map<String,ArrayList> projectsByRole: final_scope)
        {
            for (Map.Entry<String,ArrayList> entry: projectsByRole.entrySet())
            {
                if(entry.getKey()=="developer")
                {
                    entry.getValue().add(proj_id);
                }
                else if(entry.getKey()=="product_owner")
                {
                    entry.getValue().add(proj_id);
                }
                else if(entry.getKey()=="scrum_master")
                {
                    entry.getValue().add(proj_id);
                }
            }
        }
        return this;
    }

    public boolean is_authorised(int project_id, String role)
    {
        for (Map<String,ArrayList> projectsByRole: final_scope)
        {
            for (Map.Entry<String,ArrayList> entry: projectsByRole.entrySet())
            {
                if(entry.getKey()== role)
                {
                    return entry.getValue().contains(project_id); //project found and user has access rights
                }
            }
        }
        return false;
    }
    public  List<Map<String, ArrayList>> getFinal_scope()
    {
        return final_scope;
    }

    public Map<String, ArrayList> getProjects_per_developer() {
        return projects_per_developer;
    }

    public Map<String, ArrayList> getProjects_per_owner() {
        return projects_per_owner;
    }

    public Map<String, ArrayList> getProjects_per_master() {
        return projects_per_master;
    }

    public void printFinal_scope()
    {
        System.out.println(final_scope);

    }
}
