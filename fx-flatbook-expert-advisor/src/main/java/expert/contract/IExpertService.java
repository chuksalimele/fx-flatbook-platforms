/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package expert.contract;

import expert.ExpertAdvisorMQ4;

/**
 *
 * @author user
 */
public interface IExpertService extends IExpertAdvisor{

    ExpertAdvisorMQ4 getExpert();        

    public void setIsExpertEnabled(boolean b);

    public void setIsDllsAllowed(boolean b);

    public void setIsTradeAllowed(boolean b);
    
}
