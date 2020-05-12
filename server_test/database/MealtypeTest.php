<?php
declare(strict_types=1);
use PHPUnit\Framework\TestCase;
require_once(dirname(__FILE__, 3) .'\server\model\Mealtype.php');
require_once(dirname(__FILE__, 3) . '\server\database\Mealtype.php');
require_once(dirname(__FILE__, 3) . '\server\exception\NullPointerException.php');




final class MealtypeDatabaseTest extends TestCase
{

    protected $mealtype;
    protected $mock;

    public function setUp() : void{
        $this->mock = $this->createMock('database\QueryBuilder');
        $this->mock->expects($this->any())->method('getSql')->will($this->returnValue("SELECT * FROM Mealtype WHERE  name = :name"));
        $this->ingredient = new \database\Mealtype($this->mock);
    }

    public function testInsert(): void{
        $this->assertEquals(
            '00000',
            $this->ingredient->insert(new \model\Mealtype('kosher'))
        );
        $this->assertEquals(
            '23000',
            $this->ingredient->insert(new \model\Mealtype('halal'))
        );
        $this->assertEquals(
            '22001',
            $this->ingredient->insert(new \model\Mealtype('vegaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa'))
        );
    }
}
