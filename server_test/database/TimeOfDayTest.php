<?php
declare(strict_types=1);
use PHPUnit\Framework\TestCase;
require_once(dirname(__FILE__, 3) .'\server\model\TimeOfDay.php');
require_once(dirname(__FILE__, 3) . '\server\database\TimeOfDay.php');
require_once(dirname(__FILE__, 3) . '\server\database\QueryBuilder.php');
require_once(dirname(__FILE__, 3) . '\server\exception\NullPointerException.php');

final class TimeOfDayDatabaseTest extends TestCase
{
    protected $mock;
    protected $timeOfDay;

    public function setUp() : void{
        $this->mock = $this->createMock('database\QueryBuilder');
        $this->timeOfDay = new database\TimeOfDay();
    }

    public function testSelect(): void
    {
      $this->mock->expects($this->any())->method('getSql')->will($this->returnValue("SELECT * FROM TimeOfDay WHERE name = :name"));
      $this->timeOfDay = new database\TimeOfDay($this->mock);
        $this->assertEquals(
            array('00000', array(array(new \model\TimeOfDay('diner')))),
            $this->timeOfDay->select(new \model\TimeOfDay('diner'))
        );
    }

    public function testSelectNoWhere(): void
    {
      $this->mock->expects($this->any())->method('getSql')->will($this->returnValue("SELECT * FROM TimeOfDay"));
      $this->timeOfDay = new database\TimeOfDay($this->mock);
        $this->assertEquals(
            array('00000', array(array(new model\TimeOfDay('diner'), new model\TimeOfDay('lunch'), new model\TimeOfDay('ontbijt')))),
            $this->timeOfDay->select(new \model\TimeOfDay())
        );
    }

    public function testInsert(): void
    {

        $this->assertEquals(
            '00000',
            $this->timeOfDay->insert(new \model\TimeOfDay('tussendoortje'))

        );
        $this->assertEquals(
            '23000',
            $this->timeOfDay->insert(new \model\TimeOfDay('tussendoortje'))

        );
        $this->assertEquals(
            '22001',
            $this->timeOfDay->insert(new \model\TimeOfDay('THHHHHHHHHHHHHHIIIIIIIIIIIIIIIIIIIIISSSSSSSSSSSSSSSSSSSSSSSSSSIIIIIIIIIIIIIIISSSSSSSSSSSSSSSSLLLLLLONNNNNNG')),

        );
    }
}
