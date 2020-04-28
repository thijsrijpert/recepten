<?php
declare(strict_types=1);
use PHPUnit\Framework\TestCase;
require_once(dirname(__FILE__, 3) . '\server\api\Review.php');
require_once(dirname(__FILE__, 3) . '\server\exception\NullPointerException.php');

final class ReviewTest extends TestCase
{

    protected $review;

    public function setUp() : void{
        $this->review = new api\Review();
    }

    public function testErrorHandlerTitle(): void
    {
        $this->expectException(NullPointerException::class);
        $this->review->error_handler(1, 'Undefined index: title', 1, 1);
    }

    public function testErrorHandlerTitle(): void
    {
        $this->expectException(NullPointerException::class);
        $this->review->error_handler(1, 'Undefined index: description', 1, 1);
    }

    public function testErrorHandlerTitle(): void
    {
        $this->expectException(NullPointerException::class);
        $this->review->error_handler(1, 'Undefined index: rating', 1, 1);
    }

    public function testErrorHandlerTitle(): void
    {
        $this->expectException(NullPointerException::class);
        $this->review->error_handler(1, 'Undefined index: username', 1, 1);
    }
}
