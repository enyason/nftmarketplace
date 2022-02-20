package com.enyason.nftmarketplace.controller

import com.enyason.nftmarketplace.exception.NFTNotFoundException
import com.enyason.nftmarketplace.model.NFT
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class NftmarketplaceController {

    @Value("\${company_name}")
    lateinit var companyName: String

    @GetMapping("/homepage")
    fun getHomepage() = "$companyName NFT market place"

    @GetMapping("/")
    fun getNfts() = NFTs

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    fun postNFT(@RequestBody nft: NFT): NFT {
        val maxId = NFTs.map { it.id }.maxOrNull() ?: 0
        val nextId = maxId + 1
        val newNft = NFT(id = nextId, name = nft.name, floor_price = nft.floor_price)
        NFTs.add(newNft)
        return newNft
    }

    @GetMapping("/{id}")
    fun getNFTById(@PathVariable id: Int): NFT {
        return NFTs.firstOrNull { it.id == id } ?: throw NFTNotFoundException()
    }


    companion object {

        private var NFTs = mutableListOf(
            NFT(1, "CryptoPunks", 100.0),
            NFT(2, "Sneaky Vampire Syndicate", 36.9),
            NFT(3, "The Sevens (Official)", 0.6),
            NFT(4, "Art Blocks Curated", 1.1),
            NFT(5, "Pudgy Penguins", 2.5),
        )
    }
}