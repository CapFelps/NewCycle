using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using NewCycleApi.Data;
using NewCycleApi.Models;
using NewCycleApi.ViewModels;

namespace NewCycleApi.Controllers;

[ApiController]
[Route("api/[controller]")]
public class ColetasController : ControllerBase
{
    private readonly AppDbContext _context;

    public ColetasController(AppDbContext context)
    {
        _context = context;
    }

    [HttpGet]
    public async Task<ActionResult<IEnumerable<ColetaViewModel>>> GetAll([FromQuery]int page = 1, [FromQuery]int pageSize = 10)
    {
        var skip = (page - 1) * pageSize;
        var itens = await _context.Coletas
            .AsNoTracking()
            .Skip(skip)
            .Take(pageSize)
            .Select(r => new ColetaViewModel(r.Id, r.Data))
            .ToListAsync();
        return Ok(itens);
    }
}
