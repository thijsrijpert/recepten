<?php
namespace model;
require_once(dirname(__FILE__, 1) . '/Model.php');
require_once(dirname(__FILE__, 2) . '/exception/ModelNullException.php');
class User extends Model{
    private $username;
    private $password;
    private $role;
    private $token;

    public function __construct(String $username = null, \api\Hash $password = null, String $token = null, \model\Role $role = null){
        $this->username = $username;
        $this->password = $password;
        $this->role = $role;
        $this->token = $token;
    }

    public function getUsername() : String {
        if($this->username != null){
              return $this->username;
        }
        throw new ModelNullException("Username is not set");
    }

    public function setUsername(String $username) {
        $this->username = $username;
    }


    public function getPassword() : \api\Hash
    {
      if($this->password != null){
            return $this->password;
      }
      throw new ModelNullException("Password is not set");
    }

    public function setPassword(\api\Hash $password)
    {
        $this->password = $password;
    }

    public function getRole() : \model\Role
    {
      if($this->role != null){
            return $this->role;
      }
      throw new ModelNullException("Role is not set");
    }

    public function setRole(\model\Role $role)
    {
        $this->role = $role;
    }

    public function getToken() : String
    {
      if($this->token != null){
            return $this->token;
      }
      throw new ModelNullException("Role is not set");
    }

    public function setToken(String $token)
    {
        $this->token = $token;
    }
    //get the columns this entity has
    public function getVariables(){
        return [['username'], ['password'], ['token'], ['role']];
    }
    //return the object to the UI
    public function jsonSerialize() {
        $vars = get_object_vars($this);
        $vars['password'] = null;
        $vars['username'] = null;
        $json = parent::setUpJson($vars);
        return $json;
    }
}
?>
