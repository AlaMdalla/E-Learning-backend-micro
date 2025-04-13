package E_Learning.Project.Service;

import E_Learning.Project.Entity.Problem;
import E_Learning.Project.Entity.Submition;

import java.util.List;

public interface SubmitionService {
<<<<<<< HEAD
    public   Problem submitProblem(Submition submition,int userId,int idProblem);
=======
    public   Problem submitProblem(Submition submition,int idProblem);
>>>>>>> origin/job
    public Submition Update (Submition submition ,Integer id);
    public List<Submition> getSubmitions();
}
