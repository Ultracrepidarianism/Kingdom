package ca.ultracrepidarianism.kingdom.commands.subcommands;

import ca.ultracrepidarianism.kingdom.model.KDChunk;
import ca.ultracrepidarianism.kingdom.model.KDClaim;
import ca.ultracrepidarianism.kingdom.model.enums.PermissionLevelEnum;
import ca.ultracrepidarianism.kingdom.model.KDPlayer;
import ca.ultracrepidarianism.kingdom.utils.KDUtil;
import jakarta.persistence.TypedQuery;
import org.bukkit.entity.Player;
import ca.ultracrepidarianism.kingdom.commands.SubCommand;

import java.util.*;
import java.util.stream.Collectors;

public class UnclaimCommand extends SubCommand {
    @Override
    public String getPermission() {
        return "kingdom.unclaim";
    }

    @Override
    public String getLabel() {
        return "unclaim";
    }

    @Override
    public String getUsage() {
        return "/kd unclaim";
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void perform(Player ply, String[] args) {
        KDPlayer kdPlayer = database.getPlayer(ply);
        if(kdPlayer == null){
            ply.sendMessage(KDUtil.getMessage("error.kingdom.nokingdom"));
            return;
        }

        if(!kdPlayer.getPermissionLevel().hasPermission(PermissionLevelEnum.OFFICER)){
            ply.sendMessage(KDUtil.getMessage("error.kingdom.permissionLevel"));
            return;
        }

        KDClaim claim = database.getClaimFromChunk(KDChunk.parse(ply.getLocation().getChunk()));
        if(claim == null){
            ply.sendMessage(KDUtil.getMessage("error.kingdom.notclaimed"));
            return;
        }
        if(claim.getKingdom().getId() != kdPlayer.getKingdom().getId()){
            ply.sendMessage(KDUtil.getMessage("error.kingdom.donotown"));
            return;
        }

        TypedQuery<KDChunk> query = HibernateUtil.getEntityManager().createQuery("SELECT chunk FROM KDClaim WHERE kingdom.id = :kingdomId",KDChunk.class);
        query.setParameter("kingdomId",kdPlayer.getKingdom().getId());
        Set<KDChunk> chunks = query.getResultStream().collect(Collectors.toSet());
        if(chunks.size() >1){

        }
        KDChunk c = claim.getChunk();
        Set<KDChunk> chunksToReach = new HashSet<>();

        KDChunk temp = new KDChunk(c.getWorld(),c.getX() - 1,c.getZ());
        if(chunks.contains(temp))
            chunksToReach.add(temp);

        temp = new KDChunk(c.getWorld(),c.getX() + 1,c.getZ());
        if(chunks.contains(temp))
            chunksToReach.add(temp);

        temp = new KDChunk(c.getWorld(),c.getX(),c.getZ() - 1);
        if(chunks.contains(temp))
            chunksToReach.add(temp);

        temp = new KDChunk(c.getWorld(),c.getX(),c.getZ() + 1);
        if(chunks.contains(temp))
            chunksToReach.add(temp);


        chunks.remove(claim.getChunk());
        System.out.println(chunksToReach.size());
        Optional<KDChunk> firstChunk = chunksToReach.stream().findFirst();
        if(firstChunk.isPresent()){

            System.out.println(canUnclaim(firstChunk.get(),chunks,chunksToReach, new HashSet<>()));
        }

    }
    Queue<KDChunk> queuedChunk = new LinkedList<>();
    private boolean canUnclaim(KDChunk chunk,Set<KDChunk> allChunks,Set<KDChunk> chunksToReach,Set<KDChunk> chunksReached){
        if(chunk == null)
            return false;

        chunksReached.add(chunk);
        chunksToReach.remove(chunk);

        if(chunksToReach.size() == 0){
            return true;
        }

        KDChunk N = new KDChunk(chunk.getWorld(),chunk.getX(), chunk.getZ() -1);
        KDChunk S = new KDChunk(chunk.getWorld(),chunk.getX(), chunk.getZ() +1);
        KDChunk E = new KDChunk(chunk.getWorld(),chunk.getX() +1, chunk.getZ());
        KDChunk W = new KDChunk(chunk.getWorld(),chunk.getX() -1, chunk.getZ());

        if(allChunks.contains(N) && !chunksReached.contains(N))
            queuedChunk.add(N);
        if(allChunks.contains(S) && !chunksReached.contains(S))
            queuedChunk.add(S);
        if(allChunks.contains(E) && !chunksReached.contains(E))
            queuedChunk.add(E);
        if(allChunks.contains(W) && !chunksReached.contains(W))
            queuedChunk.add(W);

        return canUnclaim(queuedChunk.poll(),allChunks,chunksToReach,chunksReached);

    }
//
//    private void deleteClaim(KDClaim claim){
//        database.removeClaim(claim);
//    }

}
