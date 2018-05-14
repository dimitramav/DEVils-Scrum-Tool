package ys09.auth;

import ys09.auth.AuthScope;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

import java.lang.String;

public class ProjectsPerUser {
    public static List<Map<Integer,AuthScope>> users_permissions=new ArrayList<>();


    public static void add_user_permissions(int id, AuthScope projects)  //add a user with his
    {                                                                        //access rights when he logs in
        Map<Integer,AuthScope> new_user_scope=new HashMap<Integer,AuthScope>();
        new_user_scope.put(id,projects);
        users_permissions.add(new_user_scope);
        print_users_permissions();
    }

    public static void remove_user_permissions(int id)  //remove user with his access rights
    {                                                                //when he logs out
        print_users_permissions();
        for(Map<Integer,AuthScope> entry: users_permissions)
        {
            for(Integer key:entry.keySet())
            {
                if(key==id)
                    entry.remove(key);
            }
        }
    }

    public static boolean is_authorized(int id, int project_id,String role)
    {
        for (Map<Integer,AuthScope> projectsByRole:users_permissions)
        {
            for (Map.Entry<Integer,AuthScope> entry: projectsByRole.entrySet())
            {
                if(entry.getKey()==id) //user found
                {
                    if (entry.getValue().is_authorised(project_id, role)) //check if user has rights
                        return true;

                }
            }
        }
        return false;
    }


    public static void print_users_permissions()
    {
        for (Map<Integer,AuthScope> projectsByRole:users_permissions)
        {
            for (Map.Entry<Integer,AuthScope> entry: projectsByRole.entrySet())
            {
                System.out.print(entry.getKey()+":");
                entry.getValue().printFinal_scope();
            }
        }
    }

}
