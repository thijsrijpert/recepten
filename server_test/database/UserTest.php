<?php
declare(strict_types=1);
use PHPUnit\Framework\TestCase;
require_once(dirname(__FILE__, 3) .'\server\model\User.php');
require_once(dirname(__FILE__, 3) . '\server\database\User.php');
require_once(dirname(__FILE__, 3) . '\server\database\QueryBuilder.php');
require_once(dirname(__FILE__, 3) . '\server\exception\NullPointerException.php');

final class UserDatabaseTest extends TestCase
{
    protected $mock;
    protected $user;

    public function setUp() : void{
        $this->mock = $this->createMock('database\QueryBuilder');
        $this->user = new database\User();
    }

    public function testSelect(): void
    {
      $this->mock->expects($this->any())->method('getSql')->will($this->returnValue("SELECT * FROM User WHERE username = :username"));
      $this->user = new database\User($this->mock);
        $this->assertEquals(
            array('00000', array(array(new \model\User('user', 'ABCD', 'ABCDE','user', 123456789, 5)))),
            $this->user->select(new \model\User('user'))
        );
    }

    public function testSelectNoWhere(): void
    {
      $this->mock->expects($this->any())->method('getSql')->will($this->returnValue("SELECT * FROM TimeOfDay"));
      $this->user = new database\User($this->mock);
        $this->assertEquals(
            array('00000', array(array(new \model\User('user', 'ABCD', 'ABCDE','user', 123456789, 5)))),
            $this->user->select(new \model\User())
        );
    }

    public function testInsert(): void
    {

        $this->assertEquals(
            '00000',
            $this->user->insert(new \model\User('user1', 'ABCDE', 'ABCDEF', null, 1234567890, 5))

        );
        $this->assertEquals(
            '23000',
            $this->user->insert(new \model\User('user1', 'ABCDE', 'ABCDEF', null, 1234567890, 5))

        );
        // $this->assertEquals(
        //     '22001',
        //     $this->timeOfDay->insert(new \model\TimeOfDay('THHHHHHHHHHHHHHIIIIIIIIIIIIIIIIIIIIISSSSSSSSSSSSSSSSSSSSSSSSSSIIIIIIIIIIIIIIISSSSSSSSSSSSSSSSLLLLLLONNNNNNG')),
        //
        // );
    }
}
