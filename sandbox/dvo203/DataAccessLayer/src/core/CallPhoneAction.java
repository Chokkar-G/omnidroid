/*******************************************************************************
 * Copyright 2009 OmniDroid - http://code.google.com/p/omnidroid 
 *  
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *  
 *     http://www.apache.org/licenses/LICENSE-2.0 
 *     
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 *******************************************************************************/
package core;

import java.util.HashMap;

import edu.nyu.cs.omnidroid.util.ExceptionMessageMap;
import edu.nyu.cs.omnidroid.util.OmnidroidException;

import android.content.Intent;
import android.net.Uri;

/**
 * CallPhoneAction can return an Intent that can be fired to perform a phone call.
 **/
public class CallPhoneAction extends Action {

  /** Parameter names */
  //TODO(londinop): Decide on the best location for all app name and action name constants for consistency
  public static final String ACTION_NAME = "Dial Number";
  public static final String APP_NAME = "Phone";
  public static final String PARAM_PHONE_NO = "Phone Number";
  private static final String PARAMS[] = {PARAM_PHONE_NO};
  private static final int PARAM_PHONE_NO_INDEX = 0;

  /** The phone number to call */
  private String VALUES[];

  /**
   * Constructs a phone call action with required parameters which can provide an intent that will
   * call a phone number when fired.
   * 
   * @param parameters
   *          A map of parameters required to perform a call phone action. <br>
   *          Required parameters: <br>
   *          <ol>
   *          <li>Phone Number:
   *          <ul>
   *          <li>Key - CallPhoneAction.PARAM_PHONE_NO
   *          <li>Value - A valid phone number String.
   *          </ul>
   *          </ol>
   * @throws OmnidroidException
   *           if Action Parameter "Phone Number" is not found
   */
  public CallPhoneAction(HashMap<String, String> parameters) throws OmnidroidException {
    super(Intent.ACTION_CALL, Action.BY_ACTIVITY);
    
    VALUES = new String[PARAMS.length];
    
    VALUES[PARAM_PHONE_NO_INDEX] = parameters.get(PARAM_PHONE_NO);
    if (VALUES[PARAM_PHONE_NO_INDEX] == null) {
      throw new OmnidroidException(120002, ExceptionMessageMap.getMessage(new Integer(120002)
          .toString()));
    }
  }

  /**
   * Returns an intent that can be fired to perform a phone call action.
   * 
   * @return An intent that calls a phone number when fired.
   */
  @Override
  public Intent getIntent() {
    // TODO(Rutvij): Fetch "tel:" from constants of another class (if any).
    Intent intent = new Intent(getActionName(), Uri.parse("tel:" + VALUES[PARAM_PHONE_NO_INDEX]));
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    return intent;
  }

  @Override
  public int getNumberOfParameters() {
    return PARAMS.length;
  }

  @Override
  public String getParameter(int index) throws IndexOutOfBoundsException {
    return VALUES[index];
  }

  @Override
  public void setParameter(int index, String value) throws IndexOutOfBoundsException {
    VALUES[index] = value; 
  }

}
