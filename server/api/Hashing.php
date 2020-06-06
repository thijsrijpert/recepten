<?php
namespace Api;

class Hash{

  private static $secretnum = 10;

private static function hashing(\model\User $user){


  $hasharray = \unpack("C*", $user->getPassword());
  $hash = "";
  for($i = 0; $i < \count($hasharray); $i++ ){
    $hash .= dechex(ord(chr(($hasharray[$i] * self::$secretnum + $user->getSalt()) % 128)));
  }
  $user->setPassword($hash);
  return $user;
}

static function hashing_generate(\model\User $user){

  $user->setIteration(\random_int(5,10));
  $user->setSalt(\random_int(1000,9000));
  self::hashing($user);
  return $user;

}

static function hashing_verify(\model\User $newuser, \model\User $olduser){

  $newuser->setSalt($olduser->getSalt());
  $newuser->setIteration($olduser->getIteration());
  $newuser = self::hashing($newuser);
  return $olduser->getPassword() === $newuser->getPassword();

}

}


?>
