<?php
declare(strict_types=1);
use PHPUnit\Framework\TestCase;
require_once(dirname(__FILE__, 3) .'\server\model\Review.php');
require_once(dirname(__FILE__, 3) . '\server\database\Review.php');

final class ReviewDatabaseTest extends TestCase
{

    protected $review;

    public function setUp() : void{
        $this->review = new database\Review();
    }

    public function testInsert(): void
    {
        $this->assertEquals(
            '00000',
            $this->review->insert(new \model\Review('Dit is een test', 'dit is een test', 3.5, 'test', new DateTime()))
        );
        $this->assertEquals(
            '23000',
            $this->review->insert(new \model\Review('Dit is een test', 'dit is een test', 3.5, 'test', new DateTime()))
        );
        $this->assertEquals(
            '22001',
            $this->review->insert(new \model\Review('THHHHHHHHHHHHHHIIIIIIIIIIIIIIIIIIIIISSSSSSSSSSSSSSSSSSSSSSSSSSIIIIIIIIIIIIIIISSSSSSSSSSSSSSSSLLLLLLONNNNNNG')),
        );
    }
}
