<?php
namespace model;
class User {
    private $username;

    public function __construct(String $username){
        $this->username = $username;
    }

    public function getUsername() : String {
        return $this->username;
    }

    public function setUsername(String $username) {
        $this->username = $username;
    }
}
?>
