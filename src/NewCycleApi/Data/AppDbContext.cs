using Microsoft.EntityFrameworkCore;
using NewCycleApi.Models;

namespace NewCycleApi.Data;

public class AppDbContext : DbContext
{
    public AppDbContext(DbContextOptions<AppDbContext> options) : base(options) { }

    public DbSet<Residuo> Residuos => Set<Residuo>();
    public DbSet<PontoColeta> PontosColeta => Set<PontoColeta>();
    public DbSet<PontoDestino> PontosDestino => Set<PontoDestino>();
    public DbSet<Coleta> Coletas => Set<Coleta>();
}
