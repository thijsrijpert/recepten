<?php
declare(strict_types=1);
use PHPUnit\Framework\TestCase;
require_once(dirname(__FILE__, 3) .'\server\model\Country.php');
require_once(dirname(__FILE__, 3) . '\server\database\Country.php');
require_once(dirname(__FILE__, 3) . '\server\exception\NullPointerException.php');




final class CountryDatabaseTest extends TestCase
{

    protected $country;
    protected $mock;

    public function setUp() : void{
        $this->mock = $this->createMock('database\QueryBuilder');
        $this->mock->expects($this->any())->method('getSql')->will($this->returnValue("SELECT * FROM Country"));
        $this->country = new database\Country($this->mock);
    }

    public function testInsert(): void{
        $this->assertEquals(
            '00000',
            $this->country->insert(new \model\Country('999','descriptiontest','usernametest'))
        );
        $this->assertEquals(
            '23000',
            $this->country->insert(new \model\Country('999','descriptiontest','usernametest'))
        );
        $this->assertEquals(
            '22001',
            $this->country->insert(new \model\Country('9999','descriptiontest','usernametest'))
        );
    }
}
